package site.offload.external.oauth.apple;


import site.offload.external.enums.ErrorMessage;
import site.offload.external.exception.UnAuthorizedException;

import java.util.List;

public record ApplePublicKeys(
        List<ApplePublicKey> keys
) {
    public ApplePublicKey getMatchedKey(String kid, String alg) throws UnAuthorizedException {
        return keys.stream()
                .filter(applePublicKey -> applePublicKey.kid().equals(kid) && applePublicKey.alg().equals(alg))
                .findFirst()
                .orElseThrow(() -> new UnAuthorizedException(ErrorMessage.INVALID_JWT_EXCEPTION));
    }
}
