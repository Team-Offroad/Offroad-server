package site.offload.offloadserver.external.oauth.apple;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.api.exception.UnAuthorizedException;
import site.offload.offloadserver.api.message.ErrorMessage;

import javax.security.sasl.AuthenticationException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApplePublicKeyGenerator {

    public PublicKey generatePublicKey(Map<String, String> tokenHeaders,
                                       ApplePublicKeysResponse applePublicKeys) throws UnAuthorizedException, NoSuchAlgorithmException, InvalidKeySpecException {
        ApplePublicKey applePublicKey = applePublicKeys.getMatchedKey(tokenHeaders.get("kid"), tokenHeaders.get("alg"));

        return getPublicKey(applePublicKey);
    }

    private PublicKey getPublicKey(ApplePublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] nBytes = Base64.getUrlDecoder().decode(publicKey.n());
        byte[] eBytes = Base64.getUrlDecoder().decode(publicKey.e());

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(1, nBytes), new BigInteger(1, eBytes));

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(publicKey.kty());
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new UnAuthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION);
        }

    }
}
