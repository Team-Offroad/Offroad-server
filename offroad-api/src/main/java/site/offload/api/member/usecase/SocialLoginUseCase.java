package site.offload.api.member.usecase;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.auth.jwt.JwtTokenProvider;
import site.offload.api.auth.jwt.TokenResponse;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.exception.OffroadException;
import site.offload.api.member.dto.response.SocialLoginResponse;
import site.offload.api.member.service.MemberService;
import sites.offload.db.member.entity.Member;
import sites.offload.db.member.repository.MemberRepository;
import sites.offload.enums.Emblem;
import sites.offload.enums.ErrorMessage;
import sites.offload.enums.SocialPlatform;
import sites.offload.external.oauth.apple.AppleSocialLoginService;
import sites.offload.external.oauth.dto.SocialLoginRequest;
import sites.offload.external.oauth.google.GoogleSocialLoginService;
import sites.offload.external.oauth.google.response.GoogleInfoResponse;

@Service
@RequiredArgsConstructor
public class SocialLoginUseCase {

    private final GoogleSocialLoginService googleSocialLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final AppleSocialLoginService appleSocialLoginService;
    private final GainedEmblemService gainedEmblemService;
    private final MemberService memberService;

    @Transactional
    public SocialLoginResponse login(SocialLoginRequest socialLoginRequest) {
        Member member = null;
        boolean isAlreadyExist = false;
        String socialPlatFormRequest = socialLoginRequest.socialPlatform().getSocialPlatform();
        if (socialPlatFormRequest.equals(SocialPlatform.GOOGLE.getSocialPlatform())) {
            GoogleInfoResponse googleInfoResponse = googleSocialLoginService.login(socialLoginRequest);
            if (memberService.isExistsBySub(googleInfoResponse.id())) {
                member = memberService.findBySub(googleInfoResponse.id());
                if (member.getCurrentCharacterName() == null) {
                    isAlreadyExist = false;
                } else {
                    isAlreadyExist = true;
                }
            } else {
                member = Member.builder()
                        .name(googleInfoResponse.name())
                        .email(googleInfoResponse.email())
                        .sub(googleInfoResponse.id())
                        .build();
                memberService.saveMember(member);
                isAlreadyExist = false;
            }
        } else if (socialPlatFormRequest.equals(SocialPlatform.APPLE.getSocialPlatform())) {
            Claims appleInfoResponse = appleSocialLoginService.login(socialLoginRequest);
            if (memberService.isExistsBySub(appleInfoResponse.get("sub", String.class))) {
                member = memberService.findBySub(appleInfoResponse.get("sub", String.class));
                if (member.getCurrentCharacterName() == null) {
                    isAlreadyExist = false;
                } else {
                    isAlreadyExist = true;
                }
            } else {
                member = Member.builder()
                        .name(socialLoginRequest.name())
                        .email(appleInfoResponse.get("email", String.class))
                        .sub(appleInfoResponse.get("sub", String.class))
                        .socialPlatform(SocialPlatform.valueOf(socialLoginRequest.socialPlatform().toString().toLowerCase()))
                        .build();
                memberService.saveMember(member);
                isAlreadyExist = false;
            }
        }

        try {
            return SocialLoginResponse.of(signUp(member.getId()), isAlreadyExist);
        } catch (NullPointerException e) {
            throw new OffroadException(ErrorMessage.MEMBER_NOTFOUND_EXCEPTION);
        }
    }

    private Member checkMember(Member member) {

        return member;
    }

    public TokenResponse signUp(Long memberId) {
        return TokenResponse.of(jwtTokenProvider.generateAccessToken(memberId), jwtTokenProvider.generateRefreshToken(memberId));
    }

    private void getDefaultEmblem(Member member) {
        if (!gainedEmblemService.isExistsByMemberAndEmblemCode(member, Emblem.OFFROAD_STARTER.getEmblemCode())) {
            gainedEmblemService.save(member, Emblem.OFFROAD_STARTER.getEmblemCode());
        }
    }


}
