package site.offload.api.member.usecase;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.auth.jwt.JwtTokenProvider;
import site.offload.api.auth.jwt.TokenResponse;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.exception.OffroadException;
import site.offload.api.member.dto.response.SocialLoginResponse;
import site.offload.api.member.service.MemberService;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.emblem.Emblem;
import site.offload.enums.response.ErrorMessage;
import site.offload.enums.member.SocialPlatform;
import site.offload.external.oauth.apple.AppleSocialLoginService;
import site.offload.external.oauth.dto.SocialLoginRequest;
import site.offload.external.oauth.google.GoogleSocialLoginService;
import site.offload.external.oauth.google.response.GoogleInfoResponse;
import site.offload.external.oauth.kakao.KakaoSocialLoginService;
import site.offload.external.oauth.kakao.response.KakaoInfoResponse;

@Service
@RequiredArgsConstructor
public class SocialLoginUseCase {

    private final GoogleSocialLoginService googleSocialLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppleSocialLoginService appleSocialLoginService;
    private final KakaoSocialLoginService kakaoSocialLoginService;
    private final GainedEmblemService gainedEmblemService;
    private final MemberService memberService;
    private final MemberStatusCacheService memberStatusCacheService;

    @Transactional
    public SocialLoginResponse login(SocialLoginRequest socialLoginRequest) {
        MemberEntity memberEntity = null;
        boolean isAlreadyExist = false;
        String socialPlatFormRequest = socialLoginRequest.socialPlatform();
        if (socialPlatFormRequest.equals(SocialPlatform.GOOGLE.getSocialPlatform())) {
            GoogleInfoResponse googleInfoResponse = googleSocialLoginService.login(socialLoginRequest);
            if (memberService.isExistsBySub(googleInfoResponse.id())) {
                memberEntity = memberService.findBySub(googleInfoResponse.id());
                if (memberEntity.getCurrentCharacterName() == null) {
                    isAlreadyExist = false;
                } else {
                    isAlreadyExist = true;
                }
                memberStatusCacheService.saveMemberStatus(MemberStatus.ACTIVE.name(), memberEntity.getId());
            } else {
                memberEntity = MemberEntity.builder()
                        .name(googleInfoResponse.name())
                        .email(googleInfoResponse.email())
                        .sub(googleInfoResponse.id())
                        .socialPlatform(SocialPlatform.valueOf(socialLoginRequest.socialPlatform()))
                        .build();
                memberService.saveMember(memberEntity);
                isAlreadyExist = false;
                memberStatusCacheService.saveMemberStatus(MemberStatus.ACTIVE.name(), memberEntity.getId());
            }
        } else if (socialPlatFormRequest.equals(SocialPlatform.APPLE.getSocialPlatform())) {
            Claims appleInfoResponse = appleSocialLoginService.login(socialLoginRequest);
            if (memberService.isExistsBySub(appleInfoResponse.get("sub", String.class))) {
                memberEntity = memberService.findBySub(appleInfoResponse.get("sub", String.class));
                if (memberEntity.getCurrentCharacterName() == null) {
                    isAlreadyExist = false;
                } else {
                    isAlreadyExist = true;
                }
                memberStatusCacheService.saveMemberStatus(MemberStatus.ACTIVE.name(), memberEntity.getId());
            } else {
                memberEntity = MemberEntity.builder()
                        .name(socialLoginRequest.name())
                        .email(appleInfoResponse.get("email", String.class))
                        .sub(appleInfoResponse.get("sub", String.class))
                        .socialPlatform(SocialPlatform.valueOf(socialLoginRequest.socialPlatform()))
                        .build();
                memberService.saveMember(memberEntity);
                isAlreadyExist = false;
                memberStatusCacheService.saveMemberStatus(MemberStatus.ACTIVE.name(), memberEntity.getId());
            }
        } else if (socialPlatFormRequest.equals(SocialPlatform.KAKAO.getSocialPlatform())) {
            KakaoInfoResponse kakaoInfoResponse = kakaoSocialLoginService.login(socialLoginRequest);
            if (memberService.isExistsBySub(kakaoInfoResponse.id())) {
                memberEntity = memberService.findBySub(kakaoInfoResponse.id());
                if (memberEntity.getCurrentCharacterName() == null) {
                    isAlreadyExist = false;
                } else {
                    isAlreadyExist = true;
                }
                memberStatusCacheService.saveMemberStatus(MemberStatus.ACTIVE.name(), memberEntity.getId());
            } else {
                memberEntity = MemberEntity.builder()
                        .name(kakaoInfoResponse.kakaoAccount().get("name").toString())
                        .email(kakaoInfoResponse.kakaoAccount().get("email").toString())
                        .sub(kakaoInfoResponse.id())
                        .socialPlatform(SocialPlatform.valueOf(socialLoginRequest.socialPlatform()))
                        .build();
                memberService.saveMember(memberEntity);
                isAlreadyExist = false;
                memberStatusCacheService.saveMemberStatus(MemberStatus.ACTIVE.name(), memberEntity.getId());
            }
        }

        try {
            return SocialLoginResponse.of(signUp(memberEntity.getId()), isAlreadyExist);
        } catch (NullPointerException e) {
            throw new OffroadException(ErrorMessage.MEMBER_NOTFOUND_EXCEPTION);
        }
    }

    private MemberEntity checkMember(MemberEntity memberEntity) {

        return memberEntity;
    }

    public TokenResponse signUp(Long memberId) {
        return TokenResponse.of(jwtTokenProvider.generateAccessToken(memberId), jwtTokenProvider.generateRefreshToken(memberId));
    }

    private void getDefaultEmblem(MemberEntity memberEntity) {
        if (!gainedEmblemService.isExistsByMemberAndEmblemCode(memberEntity, Emblem.OFFROAD_STARTER.getEmblemCode())) {
            gainedEmblemService.save(memberEntity, Emblem.OFFROAD_STARTER.getEmblemCode());
        }
    }


}
