package site.offload.offloadserver.external.oauth.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.api.exception.UnAuthorizedException;
import site.offload.offloadserver.api.message.ErrorMessage;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Map;
import java.security.Key;

@Component
public class AppleIdentityTokenParser {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Map<String, String> parseHeaders(String identityToken) {
        try {
            String encoded = identityToken.split("\\.")[0];
            String decoded = new String(Base64.getUrlDecoder().decode(encoded), StandardCharsets.UTF_8);
            return OBJECT_MAPPER.readValue(decoded, Map.class);
        } catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
            throw new UnAuthorizedException(ErrorMessage.INVALID_JWT_EXCEPTION);
        }
    }

    public Claims parseWithPublicKeyAndGetClaims(String identityToken, PublicKey publicKey) {
        try {
            return getJwtParser(publicKey)
                    .parseClaimsJws(identityToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new UnAuthorizedException(ErrorMessage.INVALID_EXPIRATION_JWT_EXCEPTION);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new UnAuthorizedException(ErrorMessage.INVALID_JWT_EXCEPTION);
        }
    }

    private JwtParser getJwtParser(Key key) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }
}
