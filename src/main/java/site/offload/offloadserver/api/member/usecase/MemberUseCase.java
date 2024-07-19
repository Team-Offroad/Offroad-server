package site.offload.offloadserver.api.member.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.character.service.CharacterService;
import site.offload.offloadserver.api.character.service.GainedCharacterService;
import site.offload.offloadserver.api.charactermotion.service.CharacterMotionService;
import site.offload.offloadserver.api.charactermotion.service.GainedCharacterMotionService;
import site.offload.offloadserver.api.emblem.service.GainedEmblemService;
import site.offload.offloadserver.api.exception.BadRequestException;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.exception.UnAuthorizedException;
import site.offload.offloadserver.api.member.dto.request.AuthAdventureRequest;
import site.offload.offloadserver.api.member.dto.request.AuthPositionRequest;
import site.offload.offloadserver.api.member.dto.request.MemberAdventureInformationRequest;
import site.offload.offloadserver.api.member.dto.request.MemberProfileUpdateRequest;
import site.offload.offloadserver.api.member.dto.response.*;
import site.offload.offloadserver.api.member.service.MemberService;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.api.place.service.PlaceService;
import site.offload.offloadserver.api.place.service.VisitedPlaceService;
import site.offload.offloadserver.api.quest.service.CompleteQuestService;
import site.offload.offloadserver.api.quest.service.ProceedingQuestService;
import site.offload.offloadserver.api.quest.service.QuestRewardService;
import site.offload.offloadserver.api.quest.service.QuestService;
import site.offload.offloadserver.common.jwt.JwtTokenProvider;
import site.offload.offloadserver.common.jwt.TokenResponse;
import site.offload.offloadserver.common.util.DistanceUtil;
import site.offload.offloadserver.db.character.entity.Character;
import site.offload.offloadserver.db.charactermotion.entity.CharacterMotion;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.place.entity.Place;
import site.offload.offloadserver.db.place.entity.PlaceArea;
import site.offload.offloadserver.db.place.entity.PlaceCategory;
import site.offload.offloadserver.db.place.entity.VisitedPlace;
import site.offload.offloadserver.db.quest.entity.ProceedingQuest;
import site.offload.offloadserver.db.quest.entity.Quest;
import site.offload.offloadserver.db.quest.entity.QuestReward;
import site.offload.offloadserver.external.aws.S3UseCase;

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
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final GainedCharacterService gainedCharacterService;
    private final PlaceService placeService;
    private final VisitedPlaceService visitedPlaceService;
    private final QuestService questService;
    private final ProceedingQuestService proceedingQuestService;
    private final S3UseCase s3UseCase;
    private final QuestRewardService questRewardService;
    private final CompleteQuestService completeQuestService;

    //단위 = meter
    // ToDo: 범위 수정
    private static final int RESTAURANT_CAFE_CULTURE_PERMIT_RADIUS = 1000;
    private static final int PARK_SPORT_PERMIT_RADIUS = 1000;
    private final GainedEmblemService gainedEmblemService;


    @Transactional(readOnly = true)
    public MemberAdventureInformationResponse getMemberAdventureInformation(final MemberAdventureInformationRequest request) {
        final Member findMember = memberService.findById(request.memberId());
        final String nickname = findMember.getNickName();
        final String emblemName = findMember.getCurrentEmblemName();
        final Character findCharacter = characterService.findByName(findMember.getCurrentCharacterName());

        // 사용자가 획득한 캐릭터인지 확인
        if (!gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(findMember, findCharacter)) {
            throw new BadRequestException(ErrorMessage.NOT_GAINED_CHARACTER);
        }

        // 존재하는 장소 카테고리인지 확인
        if (!PlaceCategory.isExistsCategory(request.category())) {
            throw new NotFoundException(ErrorMessage.PLACE_CATEGORY_NOTFOUND_EXCEPTION);
        }

        final PlaceCategory placeCategory = PlaceCategory.valueOf(request.category().toUpperCase());
        final String motionImageUrl = getMotionImageUrl(placeCategory, findCharacter, findMember);
        final String baseImageUrl = findCharacter.getCharacterBaseImageUrl();
        return MemberAdventureInformationResponse.of(nickname, emblemName,
                s3UseCase.getPresignUrl(baseImageUrl), s3UseCase.getPresignUrl(motionImageUrl), findCharacter.getName());
    }

    private String getMotionImageUrl(final PlaceCategory placeCategory, final Character character, final Member member) {

        // 카테고리가 없는 장소에 있을 경우 null 반환
        if (placeCategory.equals(PlaceCategory.NONE)) {
            return null;
        }

        // 그 외의 경우에는 특정 캐릭터, 특정 카테고리에 해당하는 모션 이미지 url 반환
        final CharacterMotion findCharacterMotion = characterMotionService.findByCharacterAndPlaceCategory(character, placeCategory);

        //유저가 획득한 모션인지 확인
        if (isMemberGainedMotion(findCharacterMotion, member)) {
            return findCharacterMotion.getMotionImageUrl();
            //아니라면 null 반환
        } else {
            return null;
        }
    }

    private boolean isMemberGainedMotion(final CharacterMotion characterMotion, final Member member) {
        return gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotion, member);
    }

    @Transactional
    public void updateMemberProfile(Long memberId, MemberProfileUpdateRequest memberProfileUpdateRequest) {
        final Member findMember = memberService.findById(memberId);
        findMember.updateProfile(memberProfileUpdateRequest);
    }

    public TokenReissueResponse reissueTokens(final Long memberId, final String refreshToken) {
        if (isRefreshTokenValidate(memberId, refreshToken)) {
            TokenResponse tokenResponse = jwtTokenProvider.reissueToken(memberId);
            return TokenReissueResponse.of(tokenResponse.accessToken(), tokenResponse.refreshToken());
        } else {
            throw new UnAuthorizedException(ErrorMessage.JWT_REISSUE_EXCEPTION);
        }
    }

    private boolean isRefreshTokenValidate(final Long memberId, final String refreshToken) {
        final String findMemberId = redisTemplate.opsForValue().get(refreshToken);
        return findMemberId.equals(String.valueOf(memberId));
    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        return memberService.isExistsByNickname(nickname);
    }

    @Transactional
    public ChooseCharacterResponse chooseCharacter(Long memberId, Integer characterId) {
        final Member findMember = memberService.findById(memberId);
        final Character findCharacter = characterService.findById(characterId);
        findMember.chooseCharacter(findCharacter.getName());
        if (!gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(findMember, findCharacter)) {
            gainedCharacterService.saveGainedCharacter(findMember, findCharacter);
            gainedEmblemService.save(findMember, "TT000009");
        }
        return ChooseCharacterResponse.of(s3UseCase.getPresignUrl(findCharacter.getCharacterSpotLightImageUrl()));
    }

    @Transactional
    public VerifyPositionDistanceResponse authAdventurePosition(final Long memberId, final AuthPositionRequest request) {
        final Place findPlace = placeService.findPlaceById(request.placeId());
        final Member findMember = memberService.findById(memberId);
        final Character findCharacter = characterService.findByName(findMember.getCurrentCharacterName());

        // 클라이언트에서 받은 위도 경도 값을 카테고리 별 오차 범위 계산해서 PlaceId에 해당하는 장소의 위도 경도값과 비교
        if (!isValidLocation(request.latitude(), request.longitude(), findPlace.getLatitude(), findPlace.getLongitude(), findPlace.getPlaceCategory())) {
            return VerifyPositionDistanceResponse.of(false, s3UseCase.getPresignUrl(findCharacter.getCharacterAdventureLocationFailureImageUrl()));
        } else {
            authSucceedProcess(findMember, findPlace);
            return VerifyPositionDistanceResponse.of(true, s3UseCase.getPresignUrl(findCharacter.getCharacterAdventureSuccessImageUrl()));
        }
    }

    private void authSucceedProcess(Member member, Place place) {
        //방문한 장소 레코드 추가
        handleVisitedPlace(member, place);

        //관련된 퀘스트 모두 불러오기
        final List<Quest> quests = getQuests(place);

        //퀘스트 달성도, 인증 업데이트
        processQuests(member, place, quests);
    }

    @Transactional
    public VerifyQrcodeResponse authAdventure(final Long memberId, final AuthAdventureRequest request) {
        final Place findPlace = placeService.findPlaceById(request.placeId());
        final Member findMember = memberService.findById(memberId);
        final Character findCharacter = characterService.findByName(findMember.getCurrentCharacterName());

        // 클라이언트에서 받은 위도 경도 값을 카테고리 별 오차 범위 계산해서 PlaceId에 해당하는 장소의 위도 경도값과 비교
        if (!isValidLocation(request.latitude(), request.longitude(), findPlace.getLatitude(), findPlace.getLongitude(), findPlace.getPlaceCategory())) {
            throw new BadRequestException(ErrorMessage.NOT_ALLOWED_DISTANCE_EXCEPTION);
        }

        //qr코드 일치 확인
        if (findPlace.isValidOffroadCode(request.qrCode())) {
            authSucceedProcess(findMember, findPlace);
            return VerifyQrcodeResponse.of(true, s3UseCase.getPresignUrl(findCharacter.getCharacterAdventureSuccessImageUrl()));
        } else {
            return VerifyQrcodeResponse.of(false, s3UseCase.getPresignUrl(findCharacter.getCharacterAdventureQRFailureImageUrl()));
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

    private void handleVisitedPlace(final Member findMember, final Place findPlace) {
        visitedPlaceService.save(VisitedPlace.create(findMember, findPlace));
    }

    private List<Quest> getQuests(final Place findPlace) {
        final List<Quest> quests = new ArrayList<>();

        //조건이 NONE이 아닌 카테고리
        if (findPlace.getPlaceCategory() != PlaceCategory.NONE) {
            quests.addAll(questService.findAllByPlaceCategory(findPlace.getPlaceCategory()));
        }

        //조건이 NONE이 아닌 구역
        if (findPlace.getPlaceArea() != PlaceArea.NONE) {
            quests.addAll(questService.findAllByPlaceArea(findPlace.getPlaceArea()));
        }

        //그외
        quests.addAll(questService.findAllByPlaceAreaAndPlaceCategory(PlaceCategory.NONE, PlaceArea.NONE));

        return quests;
    }

    private void processQuests(final Member findMember, final Place findPlace, final List<Quest> quests) {
        for (Quest quest : quests) {
            // 완료된 퀘스트인지 확인
            if (completeQuestService.isExsistsByQuestAndMember(quest, findMember)) {
                continue;
            }

            // quest.getId()에 하나씩 값을 대입한 이유-> 데모데이 이전 현재시점에서 보상 목록을 전부 DB에 저장해놓고
            // 있지 않아, handleCompleteQuest()에서 Reward 가져올 시 NPE발생
            // TODO: 앱잼 이후, Reward 목록 전부 DB에 저장이후 if문 삭제
            if (quest.getId() == 5 || quest.getId() == 11 || quest.getId() == 14 || quest.getId() == 17
                    || quest.getId() == 42 || quest.getId() == 43 || quest.getId() == 44) {
                ProceedingQuest proceedingQuest;
                // 퀘스트 진행 내역이 존재하면
                if (proceedingQuestService.existsByMemberAndQuest(findMember, quest)) {
                    proceedingQuest = updateProceedingQuest(findMember, findPlace, quest);
                } else {
                    proceedingQuest = proceedingQuestService.save(ProceedingQuest.create(findMember, quest));
                }

                // 퀘스트 필요 달성도와 진행도가 일치할 경우
                if (proceedingQuest.getCurrentClearCount() == quest.getTotalRequiredClearCount()) {
                    handleQuestComplete(findMember, proceedingQuest);
                }
            }
        }
    }

    private ProceedingQuest updateProceedingQuest(final Member findMember, final Place findPlace, final Quest quest) {
        final ProceedingQuest proceedingQuest = proceedingQuestService.findByMemberAndQuest(findMember, quest);

        //'같은 장소' 여러번 방문이 조건인 퀘스트가 아닌 경우
        if (!quest.isQuestSamePlace()) {
            proceedingQuestService.addCurrentClearCount(proceedingQuest);

            //'같은 장소' 여러번 방문이 조건인 퀘스트인 경우
        } else {
            final long count = visitedPlaceService.countByMemberAndPlace(findMember, findPlace);
            //VisitedPlace의 컬럼 수를 count후 진행도에 set
            proceedingQuestService.updateCurrentClearCount(proceedingQuest, (int) count);
        }
        return proceedingQuest;
    }


    //TODO : 앱잼 이후 구현 필수
    private void handleQuestComplete(final Member findMember, final ProceedingQuest proceedingQuest) {
        final Character character = characterService.findByName(findMember.getCurrentCharacterName());

        final Quest quest = proceedingQuest.getQuest();

        final QuestReward questReward = questRewardService.findByQuestId(quest.getId());

        if (questReward.getRewardList().isCharacterMotion()) {
            CharacterMotion characterMotion = characterMotionService.findByCharacterAndPlaceCategory(character, quest.getPlaceCategory());
            if (!gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotion, findMember)) {
                gainedCharacterMotionService.save(findMember, characterMotion);
            }
        }

        if (questReward.getRewardList().getEmblemCode() != null) {
            if (!gainedEmblemService.isExistsByMemberAndEmblemCode(findMember, questReward.getRewardList().getEmblemCode())) {
                gainedEmblemService.save(findMember, questReward.getRewardList().getEmblemCode());
            }
        }
        completeQuestService.saveCompleteQuest(quest, findMember);
        proceedingQuestService.deleteProceedingQuest(quest, findMember);

        // TODO: 코드 구조 개선 및 다른 보상 획득 추가
    }

}

