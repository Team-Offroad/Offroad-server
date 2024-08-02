package site.offload.api.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.character.service.CharacterService;
import site.offload.api.charactermotion.service.CharacterMotionService;
import site.offload.api.charactermotion.service.GainedCharacterMotionService;
import site.offload.api.coupon.service.CouponService;
import site.offload.api.coupon.service.GainedCouponService;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.exception.BadRequestException;
import site.offload.api.member.dto.request.AuthAdventureRequest;
import site.offload.api.member.dto.request.AuthPositionRequest;
import site.offload.api.member.dto.response.VerifyPositionDistanceResponse;
import site.offload.api.member.dto.response.VerifyQrcodeResponse;
import site.offload.api.member.service.MemberService;
import site.offload.api.place.service.PlaceService;
import site.offload.api.place.service.VisitedPlaceService;
import site.offload.api.quest.dto.response.CompleteQuestResponse;
import site.offload.api.quest.service.CompleteQuestService;
import site.offload.api.quest.service.ProceedingQuestService;
import site.offload.api.quest.service.QuestRewardService;
import site.offload.api.quest.service.QuestService;
import site.offload.common.util.DistanceUtil;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.charactermotion.entity.CharacterMotionEntity;
import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.place.entity.PlaceEntity;
import site.offload.db.place.entity.VisitedPlaceEntity;
import site.offload.db.quest.entity.ProceedingQuestEntity;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.db.quest.entity.QuestRewardEntity;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;
import site.offload.enums.response.ErrorMessage;
import site.offload.external.aws.S3Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthAdventureUseCase {

    private final MemberService memberService;
    private final CharacterService characterService;
    private final CharacterMotionService characterMotionService;
    private final GainedCharacterMotionService gainedCharacterMotionService;
    private final PlaceService placeService;
    private final VisitedPlaceService visitedPlaceService;
    private final QuestService questService;
    private final ProceedingQuestService proceedingQuestService;
    private final S3Service s3Service;
    private final QuestRewardService questRewardService;
    private final CompleteQuestService completeQuestService;
    private final GainedCouponService gainedCouponService;
    private final GainedEmblemService gainedEmblemService;
    private final CouponService couponService;

    // 거리 단위: meter
    private static final int RESTAURANT_CAFE_CULTURE_PERMIT_RADIUS = 45;
    private static final int PARK_SPORT_PERMIT_RADIUS = 120;

    @Transactional
    public VerifyPositionDistanceResponse authAdventurePosition(final Long memberId, final AuthPositionRequest request) {
        final PlaceEntity findPlaceEntity = placeService.findPlaceById(request.placeId());
        final MemberEntity findMemberEntity = memberService.findById(memberId);
        final CharacterEntity findCharacterEntity = characterService.findByName(findMemberEntity.getCurrentCharacterName());

        // 클라이언트에서 받은 위도 경도 값을 카테고리 별 오차 범위 계산해서 PlaceId에 해당하는 장소의 위도 경도값과 비교
        if (!isValidLocation(request.latitude(), request.longitude(), findPlaceEntity.getLatitude(), findPlaceEntity.getLongitude(), findPlaceEntity.getPlaceCategory())) {
            return VerifyPositionDistanceResponse.of(false, s3Service.getPresignUrl(findCharacterEntity.getCharacterAdventureLocationFailureImageUrl()), null);
        } else {
            List<CompleteQuestResponse> completeQuestList = authSucceedProcess(findMemberEntity, findPlaceEntity);
            return VerifyPositionDistanceResponse.of(true, s3Service.getPresignUrl(findCharacterEntity.getCharacterAdventureSuccessImageUrl()), completeQuestList);
        }
    }

    private List<CompleteQuestResponse> authSucceedProcess(MemberEntity memberEntity, PlaceEntity placeEntity) {
        handleVisitedPlace(memberEntity, placeEntity);
        final List<QuestEntity> questEntities = getQuests(placeEntity);
        return processQuests(memberEntity, placeEntity, questEntities);
    }

    @Transactional
    public VerifyQrcodeResponse authAdventure(final Long memberId, final AuthAdventureRequest request) {
        final PlaceEntity findPlaceEntity = placeService.findPlaceById(request.placeId());
        final MemberEntity findMemberEntity = memberService.findById(memberId);
        final CharacterEntity findCharacterEntity = characterService.findByName(findMemberEntity.getCurrentCharacterName());

        if (!isValidLocation(request.latitude(), request.longitude(), findPlaceEntity.getLatitude(), findPlaceEntity.getLongitude(), findPlaceEntity.getPlaceCategory())) {
            throw new BadRequestException(ErrorMessage.NOT_ALLOWED_DISTANCE_EXCEPTION);
        }

        if (findPlaceEntity.isValidOffroadCode(request.qrCode())) {
            List<CompleteQuestResponse> completeQuestList = authSucceedProcess(findMemberEntity, findPlaceEntity);
            return VerifyQrcodeResponse.of(true, s3Service.getPresignUrl(findCharacterEntity.getCharacterAdventureSuccessImageUrl()), completeQuestList);
        } else {
            return VerifyQrcodeResponse.of(false, s3Service.getPresignUrl(findCharacterEntity.getCharacterAdventureQRFailureImageUrl()), null);
        }
    }

    private boolean isValidLocation(double requestLatitude, double requestLongitude, double myLatitude, double myLongitude, PlaceCategory placeCategory) {
        double permitRadius = 0.0;

        // 장소 카테고리에 따라 허용 반경 설정
        // 식당, 카페, 문화 -> 25m + gps 오차 20
        // 공원, 스포츠 -> 100m + gps 오차 20
        if (PlaceCategory.nearBy25meterPlaceCategory().contains(placeCategory)) {
            permitRadius = RESTAURANT_CAFE_CULTURE_PERMIT_RADIUS;
        }

        if (PlaceCategory.nearBy100meterPlaceCategory().contains(placeCategory)) {
            permitRadius = PARK_SPORT_PERMIT_RADIUS;
        }

        return DistanceUtil.haversine(requestLatitude, requestLongitude, myLatitude, myLongitude) < permitRadius;
    }

    private void handleVisitedPlace(final MemberEntity findMemberEntity, final PlaceEntity findPlaceEntity) {
        visitedPlaceService.save(VisitedPlaceEntity.create(findMemberEntity, findPlaceEntity));
    }

    private List<QuestEntity> getQuests(final PlaceEntity findPlaceEntity) {
        final List<QuestEntity> questEntities = new ArrayList<>();

        //조건이 NONE이 아닌 카테고리
        if (findPlaceEntity.getPlaceCategory() != PlaceCategory.NONE) {
            questEntities.addAll(questService.findAllByPlaceCategory(findPlaceEntity.getPlaceCategory()));
        }

        //조건이 NONE이 아닌 구역
        if (findPlaceEntity.getPlaceArea() != PlaceArea.NONE) {
            questEntities.addAll(questService.findAllByPlaceArea(findPlaceEntity.getPlaceArea()));
        }

        //그외
        questEntities.addAll(questService.findAllByPlaceAreaAndPlaceCategory(PlaceCategory.NONE, PlaceArea.NONE));

        return questEntities;
    }

    private List<CompleteQuestResponse> processQuests(final MemberEntity findMemberEntity, final PlaceEntity findPlaceEntity, final List<QuestEntity> questEntities) {
        return questEntities.stream()
                .filter(questEntity -> !completeQuestService.isExistByQuestAndMember(questEntity, findMemberEntity))
                .map(questEntity -> {
                    ProceedingQuestEntity proceedingQuestEntity;
                    if (proceedingQuestService.existsByMemberAndQuest(findMemberEntity, questEntity)) {
                        proceedingQuestEntity = updateProceedingQuest(findMemberEntity, findPlaceEntity, questEntity);
                    } else {
                        proceedingQuestEntity = proceedingQuestService.save(ProceedingQuestEntity.create(findMemberEntity, questEntity));
                    }

                    if (proceedingQuestEntity.getCurrentClearCount() == questEntity.getTotalRequiredClearCount()) {
                        handleQuestComplete(findMemberEntity, proceedingQuestEntity);
                        return CompleteQuestResponse.of(questEntity.getName());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private ProceedingQuestEntity updateProceedingQuest(final MemberEntity findMemberEntity, final PlaceEntity findPlaceEntity, final QuestEntity questEntity) {
        final ProceedingQuestEntity proceedingQuestEntity = proceedingQuestService.findByMemberAndQuest(findMemberEntity, questEntity);

        //'같은 장소' 여러번 방문이 조건인 퀘스트가 아닌 경우
        if (!questEntity.isQuestSamePlace()) {
            proceedingQuestService.addCurrentClearCount(proceedingQuestEntity);

            //'같은 장소' 여러번 방문이 조건인 퀘스트인 경우
        } else {
            final long count = visitedPlaceService.countByMemberAndPlace(findMemberEntity, findPlaceEntity);
            proceedingQuestService.updateCurrentClearCount(proceedingQuestEntity, (int) count);
        }
        return proceedingQuestEntity;
    }

    private void handleQuestComplete(final MemberEntity findMemberEntity, final ProceedingQuestEntity proceedingQuestEntity) {


        final CharacterEntity characterEntity = characterService.findByName(findMemberEntity.getCurrentCharacterName());
        final QuestEntity questEntity = proceedingQuestEntity.getQuestEntity();

        completeQuestService.saveCompleteQuest(questEntity, findMemberEntity);
        proceedingQuestService.deleteProceedingQuest(questEntity, findMemberEntity);

        if (!questRewardService.isExistByQuestId(questEntity.getId())) {
            return;
        }
        final QuestRewardEntity questRewardEntity = questRewardService.findByQuestId(questEntity.getId());

        handleCharacterMotionReward(findMemberEntity, questEntity, questRewardEntity, characterEntity);
        handleEmblemReward(findMemberEntity, questRewardEntity);
        handleCouponReward(findMemberEntity, questRewardEntity);
    }

    private void handleCharacterMotionReward(final MemberEntity findMemberEntity, final QuestEntity questEntity,
                                             final QuestRewardEntity questRewardEntity, final CharacterEntity characterEntity) {
        if (questRewardEntity.getRewardList().isCharacterMotion()) {
            CharacterMotionEntity characterMotionEntity = characterMotionService.findByCharacterAndPlaceCategory(characterEntity, questEntity.getPlaceCategory());
            if (!gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotionEntity, findMemberEntity)) {
                gainedCharacterMotionService.save(findMemberEntity, characterMotionEntity);
            }
        }
    }

    private void handleEmblemReward(final MemberEntity findMemberEntity, final QuestRewardEntity questRewardEntity) {
        if (questRewardEntity.getRewardList().getEmblemCode() != null) {
            if (!gainedEmblemService.isExistsByMemberAndEmblemCode(findMemberEntity, questRewardEntity.getRewardList().getEmblemCode())) {
                gainedEmblemService.save(findMemberEntity, questRewardEntity.getRewardList().getEmblemCode());
            }
        }
    }

    private void handleCouponReward(final MemberEntity findMemberEntity, final QuestRewardEntity questRewardEntity) {
        if (questRewardEntity.getRewardList().getCouponCode() != null) {
            final CouponEntity findCouponEntity = couponService.findByCouponCode(questRewardEntity.getRewardList().getCouponCode());
            if (!gainedCouponService.isExistByMemberEntityIdAndCouponId(findMemberEntity.getId(), findCouponEntity.getId())) {
                gainedCouponService.save(GainedCouponEntity.builder()
                        .memberEntity(findMemberEntity)
                        .couponEntity(findCouponEntity)
                        .build());
            }
        }
    }
}
