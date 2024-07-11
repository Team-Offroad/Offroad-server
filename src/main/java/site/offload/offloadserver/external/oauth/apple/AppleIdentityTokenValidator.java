package site.offload.offloadserver.external.oauth.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleIdentityTokenValidator {

    @Value("${apple.iss}")
    private String iss;

    @Value("${apple.key.id}")
    private String clientId;

    public boolean isValidAppleIdentityToken(Claims claims) {
        return claims.getIssuer().contains(iss) && claims.getAudience().equals(clientId);
    }
}
