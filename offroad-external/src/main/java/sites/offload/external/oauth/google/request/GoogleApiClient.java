package sites.offload.external.oauth.google.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import sites.offload.external.oauth.google.response.GoogleInfoResponse;

@FeignClient(value = "googleClient", url = "https://www.googleapis.com/userinfo/v2/me")
public interface GoogleApiClient {

    @GetMapping
    GoogleInfoResponse googleInfo(
            @RequestHeader("Authorization") String token
    );
}
