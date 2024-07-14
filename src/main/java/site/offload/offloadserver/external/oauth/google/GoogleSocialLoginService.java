package site.offload.offloadserver.external.oauth.google;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import site.offload.offloadserver.api.member.dto.request.SocialLoginRequest;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.external.oauth.google.request.GoogleApiClient;
import site.offload.offloadserver.external.oauth.google.request.GoogleAuthApiClient;
import site.offload.offloadserver.external.oauth.google.request.GoogleAuthRequest;
import site.offload.offloadserver.external.oauth.google.response.GoogleAuthResponse;
import site.offload.offloadserver.external.oauth.google.response.GoogleInfoResponse;

import java.util.Optional;

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
        GoogleAuthResponse tokenResponse = googleAuthApiClient.googleAuth(
                socialLoginRequest.code(),
                googleClientId,
                googleClientSecret,
                googleRedirectUrl,
                "authorization_code"
        );

        log.info(tokenResponse.accessToken());
        log.info(tokenResponse.scope());

        GoogleInfoResponse userResponse = googleApiClient.googleInfo("Bearer " + tokenResponse.accessToken());

        log.info(userResponse.toString());

        if (userResponse.name() == null) {
            return Member.builder()
                    .name("오프로드")
                    .email(userResponse.email())
                    .sub(userResponse.id())
                    .socialPlatform(socialLoginRequest.socialPlatform())
                    .build();
        }

        return Member.builder()
                .name(userResponse.name())
                .email(userResponse.email())
                .sub(userResponse.id())
                .socialPlatform(socialLoginRequest.socialPlatform())
                .build();
    }

}
