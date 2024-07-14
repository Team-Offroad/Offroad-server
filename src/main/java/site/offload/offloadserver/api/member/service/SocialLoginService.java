package site.offload.offloadserver.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.member.dto.request.AppleSocialLoginRequest;
import site.offload.offloadserver.api.member.dto.request.GoogleSocialLoginRequest;
import site.offload.offloadserver.common.jwt.JwtTokenProvider;
import site.offload.offloadserver.common.jwt.TokenResponse;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.member.repository.MemberRepository;
import site.offload.offloadserver.external.oauth.apple.AppleSocialLoginService;
import site.offload.offloadserver.external.oauth.google.GoogleSocialLoginService;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final GoogleSocialLoginService googleSocialLoginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final AppleSocialLoginService appleSocialLoginService;

    @Transactional
    public TokenResponse googleLogin(GoogleSocialLoginRequest googleSocialLoginRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Member member = googleSocialLoginService.login(googleSocialLoginRequest);

        //없으면 저장
        if (member != null) {
            if (!memberRepository.existsBySub(member.getSub())) {
                memberRepository.save(member);
            } else {
                member = memberRepository.findBySub(member.getSub());
            }
            return signUp(member.getId());
        }

        return TokenResponse.of(null, null);
    }

    @Transactional
    public TokenResponse appleLogin(AppleSocialLoginRequest appleSocialLoginRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Member member = appleSocialLoginService.login(appleSocialLoginRequest);

        if (member != null) {
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
