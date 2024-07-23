package sites.offload.external.oauth.google;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sites.offload.external.oauth.dto.SocialLoginRequest;
import sites.offload.external.oauth.google.request.GoogleApiClient;
import sites.offload.external.oauth.google.request.GoogleAuthApiClient;
import sites.offload.external.oauth.google.response.GoogleAuthResponse;
import sites.offload.external.oauth.google.response.GoogleInfoResponse;

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

    public GoogleInfoResponse login(SocialLoginRequest socialLoginRequest) {
        GoogleAuthResponse googleAuthResponse = googleAuthApiClient.googleAuth(
                socialLoginRequest.code(),
                googleClientId,
                googleClientSecret,
                googleRedirectUrl,
                "authorization_code"
        );

        return googleApiClient.googleInfo("Bearer " + googleAuthResponse.accessToken());
    }

}
