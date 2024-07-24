package sites.offload.external.oauth.apple;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sites.offload.external.enums.ErrorMessage;
import sites.offload.external.exception.UnAuthorizedException;
import sites.offload.external.oauth.dto.SocialLoginRequest;

import java.security.PublicKey;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppleSocialLoginService {
    private final AppleFeignClient appleFeignClient;
    private final AppleIdentityTokenParser appleIdentityTokenParser;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;
    private final AppleIdentityTokenValidator appleIdentityTokenValidator;

    public Claims login(SocialLoginRequest socialLoginRequest) {
        Map<String, String> headers = appleIdentityTokenParser.parseHeaders(socialLoginRequest.code());
        ApplePublicKeys applePublicKeys = appleFeignClient.getApplePublicKeys();
        PublicKey publicKey = applePublicKeyGenerator.generatePublicKeyWithApplePublicKeys(headers, applePublicKeys);
        Claims claims = appleIdentityTokenParser.parseWithPublicKeyAndGetClaims(socialLoginRequest.code(), publicKey);
        validateClaims(claims);

        return claims;

    }

    private void validateClaims(Claims claims) {
        if (!appleIdentityTokenValidator.isValidAppleIdentityToken(claims)) {
            throw new UnAuthorizedException(ErrorMessage.INVALID_JWT_EXCEPTION);
        }
    }
}
