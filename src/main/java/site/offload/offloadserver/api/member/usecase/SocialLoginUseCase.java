package site.offload.offloadserver.api.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.emblem.service.GainedEmblemService;
import site.offload.offloadserver.api.exception.OffroadException;
import site.offload.offloadserver.api.member.dto.request.SocialLoginRequest;
import site.offload.offloadserver.api.member.dto.request.SocialPlatform;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.common.jwt.JwtTokenProvider;
import site.offload.offloadserver.common.jwt.TokenResponse;
import site.offload.offloadserver.db.emblem.entity.Emblem;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.member.repository.MemberRepository;
import site.offload.offloadserver.external.oauth.apple.AppleSocialLoginService;
import site.offload.offloadserver.external.oauth.google.GoogleSocialLoginService;

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

    @Transactional
    public TokenResponse login(SocialLoginRequest socialLoginRequest) {
        Member member = null;
        if (socialLoginRequest.socialPlatform().equals(SocialPlatform.GOOGLE)) {
            member = googleSocialLoginService.login(socialLoginRequest);
        } else if (socialLoginRequest.socialPlatform().equals(SocialPlatform.APPLE)) {
            member = appleSocialLoginService.login(socialLoginRequest);
        }

        try {
            return signUp(checkMember(member).getId());
        } catch (NullPointerException e){
            throw new OffroadException(ErrorMessage.MEMBER_NOTFOUND_EXCEPTION);
        }
    }

    private Member checkMember(Member member) {
        if (!memberRepository.existsBySub(member.getSub())) {
            memberRepository.save(member);
            getDefaultEmblem(member);
        } else {
            member = memberRepository.findBySub(member.getSub());
        }
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
