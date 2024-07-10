package site.offload.offloadserver.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.member.dto.request.SocialLoginRequest;
import site.offload.offloadserver.api.member.dto.request.SocialPlatform;
import site.offload.offloadserver.common.jwt.JwtTokenProvider;
import site.offload.offloadserver.common.jwt.TokenResponse;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.member.repository.MemberRepository;
import site.offload.offloadserver.external.oauth.google.GoogleSocialLoginService;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final GoogleSocialLoginService googleSocialLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public TokenResponse login(SocialLoginRequest socialLoginRequest) {
        if (socialLoginRequest.socialPlatform().equals(SocialPlatform.GOOGLE)) {
            Member member = googleSocialLoginService.login(socialLoginRequest);

            //없으면 저장
            if (!memberRepository.existsBySub(member.getSub())) {
                memberRepository.save(member);
            } else {
                member = memberRepository.findBySub(member.getSub());
            }
            return signUp(member.getId());
        }
        return TokenResponse.of(null, null);
    }

    public TokenResponse signUp(Long memberId) {
        return TokenResponse.of(jwtTokenProvider.generateAccessToken(memberId), jwtTokenProvider.generateRefreshToken(memberId));
    }
}
