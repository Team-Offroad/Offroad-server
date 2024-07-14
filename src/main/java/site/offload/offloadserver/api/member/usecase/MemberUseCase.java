package site.offload.offloadserver.api.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.character.service.GainedCharacterService;
import site.offload.offloadserver.api.charactermotion.service.CharacterMotionService;
import site.offload.offloadserver.api.charactermotion.service.GainedCharacterMotionService;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.exception.UnAuthorizedException;
import site.offload.offloadserver.api.member.dto.request.AuthAdventureRequest;
import site.offload.offloadserver.api.member.dto.request.MemberAdventureInformationRequest;
import site.offload.offloadserver.api.member.dto.response.AuthAdventureResponse;
import site.offload.offloadserver.api.member.dto.response.MemberAdventureInformationResponse;
import site.offload.offloadserver.api.character.service.CharacterService;
import site.offload.offloadserver.api.member.dto.request.MemberProfileUpdateRequest;
import site.offload.offloadserver.api.member.dto.response.TokenReissueResponse;
import site.offload.offloadserver.api.member.service.MemberService;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.api.place.service.PlaceService;
import site.offload.offloadserver.api.place.service.VisitedPlaceService;
import site.offload.offloadserver.api.quest.service.ProceedingQuestService;
import site.offload.offloadserver.api.quest.service.QuestService;
import site.offload.offloadserver.common.jwt.JwtTokenProvider;
import site.offload.offloadserver.common.jwt.TokenResponse;
import site.offload.offloadserver.db.character.entity.Character;
import site.offload.offloadserver.db.charactermotion.entity.CharacterMotion;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.place.entity.Place;
import site.offload.offloadserver.db.place.entity.PlaceArea;
import site.offload.offloadserver.db.place.entity.PlaceCategory;
import site.offload.offloadserver.db.place.entity.VisitedPlace;
import site.offload.offloadserver.db.quest.entity.ProceedingQuest;
import site.offload.offloadserver.db.quest.entity.Quest;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
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

    @Transactional(readOnly = true)
    public MemberAdventureInformationResponse getMemberAdventureInformation(final MemberAdventureInformationRequest request) {
        final Member findMember = memberService.findById(request.memberId());
        final String nickname = findMember.getNickName();
        final String emblemName = findMember.getCurrentEmblemName();
        final Character findCharacter = characterService.findById(request.characterId());
        final PlaceCategory placeCategory = PlaceCategory.valueOf(request.category());

        if (PlaceCategory.isExistsCategory(placeCategory)) {
            throw new NotFoundException(ErrorMessage.PLACE_CATEGORY_NOTFOUND_EXCEPTION);
        }

        final String imageUrl = getMotionImageUrl(placeCategory, findCharacter, findMember);

        return MemberAdventureInformationResponse.of(nickname, emblemName, imageUrl, findCharacter.getName());
    }

    private String getMotionImageUrl(final PlaceCategory placeCategory, final Character character, final Member member) {

        // 카테고리가 없는 장소에 있을 경우 기본 이미지 url 반환
        if (placeCategory.equals(PlaceCategory.NONE)) {
            return character.getCharacterBaseImageUrl();
        }

        // 그 외의 경우에는 특정 캐릭터, 특정 카테고리에 해당하는 모션 이미지 url 반환
        final CharacterMotion findCharacterMotion = characterMotionService.findByCharacterAndPlaceCategory(character, placeCategory);

        //유저가 획득한 모션인지 확인
        if (isMemberGainedMotion(findCharacterMotion, member)) {
            return findCharacterMotion.getMotionImageUrl();
        //아니라면 기본 이미지 반환
        } else {
            return character.getCharacterBaseImageUrl();
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
        final String findRefreshToken = redisTemplate.opsForValue().get(String.valueOf(memberId));
        return refreshToken.equals(findRefreshToken);
    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        if (memberService.isExistsByNickname(nickname)) {
            return true;
        }
        return false;
    }

    @Transactional
    public void chooseCharacter(Long memberId, Integer characterId) {
        final Member findMember = memberService.findById(memberId);
        final Character findCharacter = characterService.findById(characterId);
        findMember.chooseCharacter(findCharacter.getName());
        gainedCharacterService.saveGainedCharacter(findMember, findCharacter);
    }

    @Transactional
    public AuthAdventureResponse authAdventure(final Long memberId, final AuthAdventureRequest request) {
        final Place findPlace = placeService.findPlaceById(request.placeId());
        final Member findMember = memberService.findById(memberId);

        //qr코드 일치 확인
        if (isValidQrCode(request.qrCode(), findPlace)) {
            //방문한 장소 컬럼 추가
            handleVisitedPlace(findMember, findPlace);

            //관련된 퀘스트 모두 불러오기
            final List<Quest> quests = getQuests(findPlace);

            //퀘스트 달성도, 인증 업데이트
            processQuests(findMember, findPlace, quests);

            return AuthAdventureResponse.of(true);
        } else {
            return AuthAdventureResponse.of(false);
        }
    }

    private boolean isValidQrCode(final String qrCode, final Place findPlace) {
        return qrCode.equals(findPlace.getOffroadCode());
    }

    private void handleVisitedPlace(final Member findMember, final Place findPlace) {
        visitedPlaceService.save(VisitedPlace.create(findMember, findPlace));
    }

    private List<Quest> getQuests(final Place findPlace) {
        final List<Quest> quests = new ArrayList<>();

        //조건이 카테고리
        quests.addAll(questService.findAllByPlaceCategory(findPlace.getPlaceCategory()));

        //조건이 구역
        quests.addAll(questService.findAllByPlaceArea(findPlace.getPlaceArea()));

        //그외
        quests.addAll(questService.findAllByPlaceAreaAndPlaceCategory(PlaceCategory.NONE, PlaceArea.NONE));

        return quests;
    }

    private void processQuests(final Member findMember, final Place findPlace, final List<Quest> quests) {
        for (Quest quest : quests) {
            ProceedingQuest proceedingQuest;

            //퀘스트 진행 내역이 존재하면
            if (proceedingQuestService.existsByMemberAndQuest(findMember, quest)) {
                proceedingQuest = proceedingQuestService.findById(updateProceedingQuest(findMember, findPlace, quest));

            //퀘스트 진행 내역이 없다면
            } else {
                proceedingQuest =  proceedingQuestService.findById(createProceedingQuest(findMember, quest));
            }

            //퀘스트 필요 달성도와 진행도가 일치할 경우
            if (proceedingQuest.getCurrentClearCount() == quest.getTotalRequiredClearCount()) {
                handleQuestComplete(proceedingQuest);
            }
        }
    }

    private Long updateProceedingQuest(final Member findMember, final Place findPlace, final Quest quest) {
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
        return proceedingQuest.getId();
    }

    private Long createProceedingQuest(final Member findMember, final Quest quest) {
        return proceedingQuestService.save(ProceedingQuest.create(findMember, quest));
    }

    //TODO : 앱잼 이후 구현 필수
    private void handleQuestComplete(final ProceedingQuest proceedingQuest) {
    }

}

