package site.offload.offloadserver.external.oauth.apple;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.api.exception.UnAuthorizedException;
import site.offload.offloadserver.api.member.dto.request.AppleSocialLoginRequest;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.db.member.entity.Member;

import java.security.PublicKey;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleSocialLoginService {
    private final AppleFeignClient appleFeignClient;
    private final AppleIdentityTokenParser appleIdentityTokenParser;
    private final ApplePublicKeyGenerator applePublicKeyGenerator;
    private final AppleIdentityTokenValidator appleIdentityTokenValidator;

    public Member login(AppleSocialLoginRequest appleSocialLoginRequest) {
        Map<String, String> headers = appleIdentityTokenParser.parseHeaders(appleSocialLoginRequest.code());
        ApplePublicKeys applePublicKeys = appleFeignClient.getApplePublicKeys();
        PublicKey publicKey = applePublicKeyGenerator.generatePublicKeyWithApplePublicKeys(headers, applePublicKeys);
        Claims claims = appleIdentityTokenParser.parseWithPublicKeyAndGetclaims(appleSocialLoginRequest.code(), publicKey);
        validateClaims(claims);

        log.info(claims.toString());

        return Member.builder()
                .name(appleSocialLoginRequest.name())
                .email(claims.get("email", String.class))
                .sub(claims.get("sub", String.class))
                .socialPlatform(appleSocialLoginRequest.socialPlatform())
                .build();

    }

    private void validateClaims(Claims claims) {
        if (!appleIdentityTokenValidator.isValidAppleIdentityToken(claims)) {
            throw new UnAuthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION);
        }
    }
}
