package site.offload.external.oauth.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.offload.external.oauth.dto.SocialLoginRequest;
import site.offload.external.oauth.kakao.request.KakaoAccessTokenClient;
import site.offload.external.oauth.kakao.request.KakaoInfoClient;
import site.offload.external.oauth.kakao.response.KakaoInfoResponse;
import site.offload.external.oauth.kakao.response.KakaoAccessTokenResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoSocialLoginService {

    @Value("${kakao.client-id}")
    private String kakaoClientId;
    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    private final KakaoAccessTokenClient kakaoAccessTokenClient;
    private final KakaoInfoClient kakaoInfoClient;

    public KakaoInfoResponse login(SocialLoginRequest socialLoginRequest) {
        KakaoAccessTokenResponse kakaoAccessTokenResponse = kakaoAccessTokenClient.kakaoAuth(
                "application/x-www-form-urlencoded;charset=utf-8",
                socialLoginRequest.code(),
                kakaoClientId,
                kakaoRedirectUri,
                "authorization_code"
        );
        return kakaoInfoClient.kakaoInfo("Bearer " + kakaoAccessTokenResponse.accessToken(), "application/x-www-form-urlencoded;charset=utf-8");
    }
}
