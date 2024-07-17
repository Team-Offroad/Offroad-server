package site.offload.offloadserver.api.member.usecase;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.emblem.service.GainedEmblemService;
import site.offload.offloadserver.api.exception.OffroadException;
import site.offload.offloadserver.api.member.dto.request.SocialLoginRequest;
import site.offload.offloadserver.api.member.dto.request.SocialPlatform;
import site.offload.offloadserver.api.member.dto.response.SocialLoginResponse;
import site.offload.offloadserver.api.member.service.MemberService;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.common.jwt.JwtTokenProvider;
import site.offload.offloadserver.common.jwt.TokenResponse;
import site.offload.offloadserver.db.emblem.entity.Emblem;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.member.repository.MemberRepository;
import site.offload.offloadserver.external.oauth.apple.AppleSocialLoginService;
import site.offload.offloadserver.external.oauth.google.GoogleSocialLoginService;
import site.offload.offloadserver.external.oauth.google.response.GoogleInfoResponse;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
        if (socialLoginRequest.socialPlatform().equals(SocialPlatform.GOOGLE)) {
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
        } else if (socialLoginRequest.socialPlatform().equals(SocialPlatform.APPLE)) {
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
                        .socialPlatform(socialLoginRequest.socialPlatform())
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
