package site.offload.offloadserver.api.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.characterMotion.service.CharacterMotionService;
import site.offload.offloadserver.api.characterMotion.service.GainedCharacterMotionService;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.exception.UnAuthorizedException;
import site.offload.offloadserver.api.member.dto.MemberAdventureInformationRequest;
import site.offload.offloadserver.api.member.dto.MemberAdventureInformationResponse;
import site.offload.offloadserver.api.character.service.CharacterService;
import site.offload.offloadserver.api.member.dto.request.MemberProfileUpdateRequest;
import site.offload.offloadserver.api.member.dto.response.TokenReissueResponse;
import site.offload.offloadserver.api.member.service.MemberService;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.common.jwt.JwtTokenProvider;
import site.offload.offloadserver.common.jwt.TokenResponse;
import site.offload.offloadserver.db.character.entity.Character;
import site.offload.offloadserver.db.charactermotion.entity.CharacterMotion;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.place.entity.PlaceCategory;

@Service
@RequiredArgsConstructor
public class MemberUseCase {

    private final MemberService memberService;
    private final CharacterService characterService;
    private final CharacterMotionService characterMotionService;
    private final GainedCharacterMotionService gainedCharacterMotionService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

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
        if (memberService.findByNickname(nickname)) {
            return true;
        }
        return false;
    }
}

