package site.offload.external.oauth.apple;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleIdentityTokenValidator {

    @Value("${apple.iss}")
    private String iss;
    @Value("${apple.client-id}")
    private String clientId;

    public boolean isValidAppleIdentityToken(Claims claims) {
        return claims.getIssuer().contains(iss)
                && claims.getAudience().equals(clientId);
    }
}
