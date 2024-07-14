package site.offload.offloadserver.external.oauth.apple;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleFeignClient", url = "https://appleid.apple.com/auth/keys")
public interface AppleFeignClient {

    @GetMapping
    ApplePublicKeys getApplePublicKeys();
}
