package sites.offload.external.oauth.google.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sites.offload.external.oauth.google.response.GoogleAuthResponse;

@FeignClient(name = "GoogleAuthApiClient", url = "https://oauth2.googleapis.com/token")
public interface GoogleAuthApiClient {

    @PostMapping
    GoogleAuthResponse googleAuth(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "client_id") String clientId,
            @RequestParam(name = "client_secret") String clientSecret,
            @RequestParam(name = "redirect_uri") String redirectUri,
            @RequestParam(name = "grant_type") String grantType);
}
