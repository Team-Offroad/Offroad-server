package site.offload.offloadserver.external.oauth.google;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import site.offload.offloadserver.api.member.dto.request.SocialLoginRequest;
import site.offload.offloadserver.api.member.service.validator.MemberValidator;
import site.offload.offloadserver.common.jwt.JwtTokenProvider;
import site.offload.offloadserver.common.jwt.TokenResponse;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.member.repository.MemberRepository;
import site.offload.offloadserver.external.oauth.google.request.GoogleAuthRequest;
import site.offload.offloadserver.external.oauth.google.response.GoogleAuthResponse;
import site.offload.offloadserver.external.oauth.google.response.GoogleInfoResponse;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GoogleSocialLoginService {

    @Value("${google.client-id}")
    private String googleClientId;
    @Value("${google.client-secret}")
    private String googleClientSecret;
    @Value("${google.redirect-url}")
    private String googleRedirectUrl;

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponse login(SocialLoginRequest socialLoginRequest) {
        RestClient googleAuthRestClient = RestClient.create();
        GoogleAuthResponse googleAuthResponse = googleAuthRestClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_JSON)
                .body(GoogleAuthRequest.of(socialLoginRequest.code(), googleClientId, googleClientSecret, googleRedirectUrl, "authorization_code"))
                .retrieve()
                .body(GoogleAuthResponse.class);

        RestClient googleInfoRestClient = RestClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + googleAuthResponse.accessToken())
                .build();
        GoogleInfoResponse googleInfoResponse = googleInfoRestClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                .retrieve()
                .body(GoogleInfoResponse.class);

        Optional<Member> findMember = memberRepository.findByEmail(googleInfoResponse.email());
        Member member;
        if (MemberValidator.isNewMember(findMember)) {
            Member newMember = Member.builder()
                    .name(googleInfoResponse.name())
                    .email(googleInfoResponse.email())
                    .build();
            memberRepository.save(newMember);
            member = newMember;
        } else {
            member = findMember.get();
        }

        return TokenResponse.of(jwtTokenProvider.generateAccessToken(member.getId()), jwtTokenProvider.generateRefreshToken(member.getId()));
    }
}
