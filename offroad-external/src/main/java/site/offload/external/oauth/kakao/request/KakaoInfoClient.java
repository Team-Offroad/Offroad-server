package site.offload.external.oauth.kakao.request;

import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import site.offload.external.oauth.kakao.response.KakaoInfoResponse;

@FeignClient(value = "kakaoInfoClient", url = "https://kapi.kakao.com/v2/user/me")
public interface KakaoInfoClient {

    @GetMapping
    KakaoInfoResponse kakaoInfo(
            @RequestHeader("Authorization") String token,
            @RequestHeader(name = "Content-type") String contentType
    );
}
