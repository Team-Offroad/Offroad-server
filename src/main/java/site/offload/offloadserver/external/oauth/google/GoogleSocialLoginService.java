package site.offload.offloadserver.external.oauth.google;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.api.member.dto.request.SocialLoginRequest;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.external.oauth.google.request.GoogleApiClient;
import site.offload.offloadserver.external.oauth.google.request.GoogleAuthApiClient;
import site.offload.offloadserver.external.oauth.google.response.GoogleAuthResponse;
import site.offload.offloadserver.external.oauth.google.response.GoogleInfoResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleSocialLoginService {

    @Value("${google.client-id}")
    private String googleClientId;
    @Value("${google.client-secret}")
    private String googleClientSecret;
    @Value("${google.redirect-url}")
    private String googleRedirectUrl;

    private final GoogleAuthApiClient googleAuthApiClient;
    private final GoogleApiClient googleApiClient;

    public Member login(SocialLoginRequest socialLoginRequest) {
        GoogleAuthResponse googleAuthResponse = googleAuthApiClient.googleAuth(
                socialLoginRequest.code(),
                googleClientId,
                googleClientSecret,
                googleRedirectUrl,
                "authorization_code"
        );

        GoogleInfoResponse googleInfoResponse = googleApiClient.googleInfo("Bearer " + googleAuthResponse.accessToken());

        return Member.builder()
                .name(googleInfoResponse.name())
                .email(googleInfoResponse.email())
                .sub(googleInfoResponse.id())
                .socialPlatform(socialLoginRequest.socialPlatform())
                .build();
    }

}
