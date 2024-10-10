package site.offload.external.oauth.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.offload.external.oauth.dto.SocialLoginRequest;
import site.offload.external.oauth.kakao.request.KakaoInfoClient;
import site.offload.external.oauth.kakao.response.KakaoInfoResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoSocialLoginService {

    private final KakaoInfoClient kakaoInfoClient;

    public KakaoInfoResponse login(SocialLoginRequest socialLoginRequest) {
        return kakaoInfoClient.kakaoInfo("Bearer " + socialLoginRequest.code(), "application/x-www-form-urlencoded;charset=utf-8");
    }
}
