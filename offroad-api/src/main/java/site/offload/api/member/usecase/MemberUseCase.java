package site.offload.api.member.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.character.service.CharacterService;
import site.offload.api.character.service.GainedCharacterService;
import site.offload.api.charactermotion.service.CharacterMotionService;
import site.offload.api.charactermotion.service.GainedCharacterMotionService;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.exception.BadRequestException;
import site.offload.api.exception.NotFoundException;
import site.offload.api.member.dto.request.AuthAdventureRequest;
import site.offload.api.member.dto.request.AuthPositionRequest;
import site.offload.api.member.dto.request.MemberAdventureInformationRequest;
import site.offload.api.member.dto.request.MemberProfileUpdateRequest;
import site.offload.api.member.dto.response.ChooseCharacterResponse;
import site.offload.api.member.dto.response.MemberAdventureInformationResponse;
import site.offload.api.member.dto.response.VerifyPositionDistanceResponse;
import site.offload.api.member.dto.response.VerifyQrcodeResponse;
import site.offload.api.member.service.MemberService;
import site.offload.api.place.service.PlaceService;
import site.offload.api.place.service.VisitedPlaceService;
import site.offload.api.quest.service.CompleteQuestService;
import site.offload.api.quest.service.ProceedingQuestService;
import site.offload.api.quest.service.QuestRewardService;
import site.offload.api.quest.service.QuestService;
import sites.offload.common.util.DistanceUtil;
import sites.offload.db.character.entity.CharacterEntity;
import sites.offload.db.charactermotion.entity.CharacterMotionEntity;
import sites.offload.db.member.embeddable.Birthday;
import sites.offload.db.member.entity.MemberEntity;
import sites.offload.db.place.entity.PlaceEntity;
import sites.offload.db.place.entity.VisitedPlaceEntity;
import sites.offload.db.quest.entity.ProceedingQuestEntity;
import sites.offload.db.quest.entity.QuestEntity;
import sites.offload.db.quest.entity.QuestRewardEntity;
import sites.offload.enums.ErrorMessage;
import sites.offload.enums.PlaceArea;
import sites.offload.enums.PlaceCategory;
import sites.offload.external.aws.S3Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberUseCase {

    private final MemberService memberService;
    private final CharacterService characterService;
    private final CharacterMotionService characterMotionService;
    private final GainedCharacterMotionService gainedCharacterMotionService;
    private final GainedCharacterService gainedCharacterService;
    private final PlaceService placeService;
    private final VisitedPlaceService visitedPlaceService;
    private final QuestService questService;
    private final ProceedingQuestService proceedingQuestService;
    private final S3Service s3Service;
    private final QuestRewardService questRewardService;
    private final CompleteQuestService completeQuestService;

    //단위 = meter
    // ToDo: 범위 수정
    private static final int RESTAURANT_CAFE_CULTURE_PERMIT_RADIUS = 1000;
    private static final int PARK_SPORT_PERMIT_RADIUS = 1000;
    private final GainedEmblemService gainedEmblemService;


    @Transactional(readOnly = true)
    public MemberAdventureInformationResponse getMemberAdventureInformation(final MemberAdventureInformationRequest request) {
        final MemberEntity findMemberEntity = memberService.findById(request.memberId());
        final String nickname = findMemberEntity.getNickName();
        final String emblemName = findMemberEntity.getCurrentEmblemName();
        final CharacterEntity findCharacterEntity = characterService.findByName(findMemberEntity.getCurrentCharacterName());

        // 사용자가 획득한 캐릭터인지 확인
        if (!gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(findMemberEntity, findCharacterEntity)) {
            throw new BadRequestException(ErrorMessage.NOT_GAINED_CHARACTER);
        }

        // 존재하는 장소 카테고리인지 확인
        if (!PlaceCategory.isExistsCategory(request.category())) {
            throw new NotFoundException(ErrorMessage.PLACE_CATEGORY_NOTFOUND_EXCEPTION);
        }

        final PlaceCategory placeCategory = PlaceCategory.valueOf(request.category().toUpperCase());
        final String motionImageUrl = getMotionImageUrl(placeCategory, findCharacterEntity, findMemberEntity);
        final String baseImageUrl = findCharacterEntity.getCharacterBaseImageUrl();
        return MemberAdventureInformationResponse.of(nickname, emblemName,
                s3Service.getPresignUrl(baseImageUrl), s3Service.getPresignUrl(motionImageUrl), findCharacterEntity.getName());
    }

    private String getMotionImageUrl(final PlaceCategory placeCategory, final CharacterEntity characterEntity, final MemberEntity memberEntity) {

        // 카테고리가 없는 장소에 있을 경우 null 반환
        if (placeCategory.equals(PlaceCategory.NONE)) {
            return null;
        }

        // 그 외의 경우에는 특정 캐릭터, 특정 카테고리에 해당하는 모션 이미지 url 반환
        final CharacterMotionEntity findCharacterMotionEntity = characterMotionService.findByCharacterAndPlaceCategory(characterEntity, placeCategory);

        //유저가 획득한 모션인지 확인
        if (isMemberGainedMotion(findCharacterMotionEntity, memberEntity)) {
            return findCharacterMotionEntity.getMotionImageUrl();
            //아니라면 null 반환
        } else {
            return null;
        }
    }

    private boolean isMemberGainedMotion(final CharacterMotionEntity characterMotionEntity, final MemberEntity memberEntity) {
        return gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotionEntity, memberEntity);
    }

    @Transactional
    public void updateMemberProfile(Long memberId, MemberProfileUpdateRequest memberProfileUpdateRequest) {
        final MemberEntity findMemberEntity = memberService.findById(memberId);
        Birthday birthday = new Birthday(memberProfileUpdateRequest.year(), memberProfileUpdateRequest.month(), memberProfileUpdateRequest.day());
        findMemberEntity.updateProfile(memberProfileUpdateRequest.nickname(), birthday, memberProfileUpdateRequest.gender());
    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        return memberService.isExistsByNickname(nickname);
    }

    @Transactional
    public ChooseCharacterResponse chooseCharacter(Long memberId, Integer characterId) {
        final MemberEntity findMemberEntity = memberService.findById(memberId);
        final CharacterEntity findCharacterEntity = characterService.findById(characterId);
        findMemberEntity.chooseCharacter(findCharacterEntity.getName());
        if (!gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(findMemberEntity, findCharacterEntity)) {
            gainedCharacterService.saveGainedCharacter(findMemberEntity, findCharacterEntity);
            gainedEmblemService.save(findMemberEntity, "TT000009");
        }
        return ChooseCharacterResponse.of(s3Service.getPresignUrl(findCharacterEntity.getCharacterSpotLightImageUrl()));
    }

    @Transactional
    public VerifyPositionDistanceResponse authAdventurePosition(final Long memberId, final AuthPositionRequest request) {
        final PlaceEntity findPlaceEntity = placeService.findPlaceById(request.placeId());
        final MemberEntity findMemberEntity = memberService.findById(memberId);
        final CharacterEntity findCharacterEntity = characterService.findByName(findMemberEntity.getCurrentCharacterName());

        // 클라이언트에서 받은 위도 경도 값을 카테고리 별 오차 범위 계산해서 PlaceId에 해당하는 장소의 위도 경도값과 비교
        if (!isValidLocation(request.latitude(), request.longitude(), findPlaceEntity.getLatitude(), findPlaceEntity.getLongitude(), findPlaceEntity.getPlaceCategory())) {
            return VerifyPositionDistanceResponse.of(false, s3Service.getPresignUrl(findCharacterEntity.getCharacterAdventureLocationFailureImageUrl()));
        } else {
            authSucceedProcess(findMemberEntity, findPlaceEntity);
            return VerifyPositionDistanceResponse.of(true, s3Service.getPresignUrl(findCharacterEntity.getCharacterAdventureSuccessImageUrl()));
        }
    }

    private void authSucceedProcess(MemberEntity memberEntity, PlaceEntity placeEntity) {
        //방문한 장소 레코드 추가
        handleVisitedPlace(memberEntity, placeEntity);

        //관련된 퀘스트 모두 불러오기
        final List<QuestEntity> questEntities = getQuests(placeEntity);

        //퀘스트 달성도, 인증 업데이트
        processQuests(memberEntity, placeEntity, questEntities);
    }

    @Transactional
    public VerifyQrcodeResponse authAdventure(final Long memberId, final AuthAdventureRequest request) {
        final PlaceEntity findPlaceEntity = placeService.findPlaceById(request.placeId());
        final MemberEntity findMemberEntity = memberService.findById(memberId);
        final CharacterEntity findCharacterEntity = characterService.findByName(findMemberEntity.getCurrentCharacterName());

        // 클라이언트에서 받은 위도 경도 값을 카테고리 별 오차 범위 계산해서 PlaceId에 해당하는 장소의 위도 경도값과 비교
        if (!isValidLocation(request.latitude(), request.longitude(), findPlaceEntity.getLatitude(), findPlaceEntity.getLongitude(), findPlaceEntity.getPlaceCategory())) {
            throw new BadRequestException(ErrorMessage.NOT_ALLOWED_DISTANCE_EXCEPTION);
        }

        //qr코드 일치 확인
        if (findPlaceEntity.isValidOffroadCode(request.qrCode())) {
            authSucceedProcess(findMemberEntity, findPlaceEntity);
            return VerifyQrcodeResponse.of(true, s3Service.getPresignUrl(findCharacterEntity.getCharacterAdventureSuccessImageUrl()));
        } else {
            return VerifyQrcodeResponse.of(false, s3Service.getPresignUrl(findCharacterEntity.getCharacterAdventureQRFailureImageUrl()));
        }
    }

    private boolean isValidLocation(double requestLatitude, double requestLongitude, double myLatitude, double myLongitude, PlaceCategory placeCategory) {
        double permitRadius = 0.0;

        // 장소 카테고리에 따라 허용 반경 설정
        // 식당, 카페, 문화 -> 25m
        // 공원, 스포츠 -> 100m
        if (PlaceCategory.nearBy25meterPlaceCategory().contains(placeCategory)) {
            permitRadius = RESTAURANT_CAFE_CULTURE_PERMIT_RADIUS;
        }

        if (PlaceCategory.nearBy100meterPlaceCategory().contains(placeCategory)) {
            permitRadius = PARK_SPORT_PERMIT_RADIUS;
        }

        // 거리 계산 후 허용 반경과 비교하여 결과 반환
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

    private void processQuests(final MemberEntity findMemberEntity, final PlaceEntity findPlaceEntity, final List<QuestEntity> questEntities) {
        for (QuestEntity questEntity : questEntities) {
            // 완료된 퀘스트인지 확인
            if (completeQuestService.isExistByQuestAndMember(questEntity, findMemberEntity)) {
                continue;
            }

            // quest.getId()에 하나씩 값을 대입한 이유-> 데모데이 이전 현재시점에서 보상 목록을 전부 DB에 저장해놓고
            // 있지 않아, handleCompleteQuest()에서 Reward 가져올 시 NPE발생
            // TODO: 앱잼 이후, Reward 목록 전부 DB에 저장이후 if문 삭제
            if (questEntity.getId() == 5 || questEntity.getId() == 11 || questEntity.getId() == 14 || questEntity.getId() == 17
                    || questEntity.getId() == 42 || questEntity.getId() == 43 || questEntity.getId() == 44) {
                ProceedingQuestEntity proceedingQuestEntity;
                // 퀘스트 진행 내역이 존재하면
                if (proceedingQuestService.existsByMemberAndQuest(findMemberEntity, questEntity)) {
                    proceedingQuestEntity = updateProceedingQuest(findMemberEntity, findPlaceEntity, questEntity);
                } else {
                    proceedingQuestEntity = proceedingQuestService.save(ProceedingQuestEntity.create(findMemberEntity, questEntity));
                }

                // 퀘스트 필요 달성도와 진행도가 일치할 경우
                if (proceedingQuestEntity.getCurrentClearCount() == questEntity.getTotalRequiredClearCount()) {
                    handleQuestComplete(findMemberEntity, proceedingQuestEntity);
                }
            }
        }
    }

    private ProceedingQuestEntity updateProceedingQuest(final MemberEntity findMemberEntity, final PlaceEntity findPlaceEntity, final QuestEntity questEntity) {
        final ProceedingQuestEntity proceedingQuestEntity = proceedingQuestService.findByMemberAndQuest(findMemberEntity, questEntity);

        //'같은 장소' 여러번 방문이 조건인 퀘스트가 아닌 경우
        if (!questEntity.isQuestSamePlace()) {
            proceedingQuestService.addCurrentClearCount(proceedingQuestEntity);

            //'같은 장소' 여러번 방문이 조건인 퀘스트인 경우
        } else {
            final long count = visitedPlaceService.countByMemberAndPlace(findMemberEntity, findPlaceEntity);
            //VisitedPlace의 컬럼 수를 count후 진행도에 set
            proceedingQuestService.updateCurrentClearCount(proceedingQuestEntity, (int) count);
        }
        return proceedingQuestEntity;
    }


    //TODO : 앱잼 이후 구현 필수
    private void handleQuestComplete(final MemberEntity findMemberEntity, final ProceedingQuestEntity proceedingQuestEntity) {
        final CharacterEntity characterEntity = characterService.findByName(findMemberEntity.getCurrentCharacterName());

        final QuestEntity questEntity = proceedingQuestEntity.getQuestEntity();

        final QuestRewardEntity questRewardEntity = questRewardService.findByQuestId(questEntity.getId());

        if (questRewardEntity.getRewardList().isCharacterMotion()) {
            CharacterMotionEntity characterMotionEntity = characterMotionService.findByCharacterAndPlaceCategory(characterEntity, questEntity.getPlaceCategory());
            if (!gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotionEntity, findMemberEntity)) {
                gainedCharacterMotionService.save(findMemberEntity, characterMotionEntity);
            }
        }

        if (questRewardEntity.getRewardList().getEmblemCode() != null) {
            if (!gainedEmblemService.isExistsByMemberAndEmblemCode(findMemberEntity, questRewardEntity.getRewardList().getEmblemCode())) {
                gainedEmblemService.save(findMemberEntity, questRewardEntity.getRewardList().getEmblemCode());
            }
        }
        completeQuestService.saveCompleteQuest(questEntity, findMemberEntity);
        proceedingQuestService.deleteProceedingQuest(questEntity, findMemberEntity);

        // TODO: 코드 구조 개선 및 다른 보상 획득 추가
    }

}

