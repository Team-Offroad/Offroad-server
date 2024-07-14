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
        if (request.qrCode().equals(findPlace.getOffroadCode())) {
            // VisitedPlace 테이블 컬럼 추가
            visitedPlaceService.save(VisitedPlace.create(findMember, findPlace));

            // findPlace의 '카테고리'에 해당하는 '카테고리' 컬럼을 가진 퀘스트 전부 가져오기
            final List<Quest> findQuestsByPlaceCategory = questService.findAllByPlaceCategory(findPlace.getPlaceCategory());

            // findPlace의 '구역'에 해당하는 '구역' 컬럼을 가진 퀘스트 전부 가져오기
            final List<Quest> findQuestsByPlaceArea = questService.findAllByPlaceArea(findPlace.getPlaceArea());

            //PlaceCategory, PlaceArea가 'None'인 퀘스트 전부 가져오기
            final List<Quest> findQuestByPlaceAndCategoryWithNone = questService.findAllByPlaceAreaAndPlaceCategory(PlaceCategory.NONE, PlaceArea.NONE);

            //받아온 퀘스트들 모으기
            List<Quest> quests = new ArrayList<>();
            quests.addAll(findQuestsByPlaceCategory);
            quests.addAll(findQuestsByPlaceArea);
            quests.addAll(findQuestByPlaceAndCategoryWithNone);


            // ProceedingQuest 테이블 컬럼 추가
            quests.forEach(
                    quest -> {
                        ProceedingQuest proceedingQuest;
                        if (proceedingQuestService.existsByMemberAndQuest(findMember, quest)) {
                            // 퀘스트 진행도 올리기
                            proceedingQuest = proceedingQuestService.findByMemberAndQuest(findMember, quest);
                            // 같은 장소 조건이 있는 퀘스트가 아닐 경우
                            if (!quest.isQuestSamePlace()) {
                                proceedingQuest.updateCurrentClearCount();
                            // 같은 장소 조건이 있는 퀘스트 -> visistedPlace에서 count로 가져온 값을 set
                            } else {
                                final long count = visitedPlaceService.countByMemberAndPlace(findMember, findPlace);
                                proceedingQuest.setCurrentClearCount((int) count);
                            }
                        } else {
                            // 아직 퀘스트 진행 전이면, 컬럼 생성 (진행도는 엔티티에서 기본 값 1로 설정)
                            final Long proceedingQuestId = proceedingQuestService.save(ProceedingQuest.create(findMember, quest));
                            proceedingQuest = proceedingQuestService.findById(proceedingQuestId);
                        }
                        //유저의 퀘스트 진행도와 퀘스트 총 달성도가 같으면, 퀘스트 달성 && 보상 주기 -> 앱잼 이후 구현
                        if (proceedingQuest.getCurrentClearCount() == quest.getTotalRequiredClearCount()) {
                            // 로직
                        }
                    }
            );
            return AuthAdventureResponse.of(true);
        } else {
            return AuthAdventureResponse.of(false);
        }
    }
}

