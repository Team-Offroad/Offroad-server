package site.offload.external.oauth.kakao.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.external.oauth.kakao.response.KakaoAccessTokenResponse;

@FeignClient(value = "kakaoClient", url = "https://kauth.kakao.com/oauth/token")
public interface KakaoAccessTokenClient {

    @PostMapping
    KakaoAccessTokenResponse kakaoAuth(
            @RequestHeader(name = "Content-type") String contentType,
            @RequestParam(name = "code") String code,
            @RequestParam(name = "client_id") String clientId,
            @RequestParam(name = "redirect_uri") String redirectUri,
            @RequestParam(name = "grant_type") String grantType);
}
