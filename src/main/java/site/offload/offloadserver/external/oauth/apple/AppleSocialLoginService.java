package site.offload.offloadserver.external.oauth.apple;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import site.offload.offloadserver.api.exception.UnAuthorizedException;
import site.offload.offloadserver.api.member.dto.request.SocialLoginRequest;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.db.member.entity.Member;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppleSocialLoginService {

    @Value("${apple.auth.public-key-url}")
    private String applePublicKeyUrl;

    private final AppleIdentityTokenParser appleIdentityTokenParser;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;
    private final AppleIdentityTokenValidator appleIdentityTokenValidator;

    public Member login(SocialLoginRequest socialLoginRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {

        Map<String, String> headers = appleIdentityTokenParser.parseHeaders(socialLoginRequest.code());
        RestClient appleKeysRestClient = RestClient.create();
        ApplePublicKeysResponse applePublicKeysResponse = appleKeysRestClient.get()
                .uri(applePublicKeyUrl)
                .retrieve()
                .body(ApplePublicKeysResponse.class);
        PublicKey publicKey = applePublicKeyGenerator.generatePublicKey(headers, applePublicKeysResponse);
        Claims claims = appleIdentityTokenParser.parseWithPublicKeyAndGetclaims(socialLoginRequest.code(), publicKey);
        validateClaims(claims);

        return Member.builder()
                .name(claims.get("name", String.class))
                .email(claims.get("email", String.class))
                .sub(claims.getSubject())
                .socialPlatform(socialLoginRequest.socialPlatform())
                .build();

    }

    private void validateClaims(Claims claims) {
        if (!appleIdentityTokenValidator.isValidAppleIdentityToken(claims)) {
            throw new UnAuthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION);
        }
    }
}
