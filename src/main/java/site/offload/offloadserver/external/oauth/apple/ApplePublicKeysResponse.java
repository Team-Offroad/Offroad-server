package site.offload.offloadserver.external.oauth.apple;

import site.offload.offloadserver.api.exception.UnAuthorizedException;
import site.offload.offloadserver.api.message.ErrorMessage;

import java.util.List;

public record ApplePublicKeysResponse(
        List<ApplePublicKey> keys
) {
    public ApplePublicKey getMatchedKey(String kid, String alg) throws UnAuthorizedException {
        return keys.stream()
                .filter(key -> key.kid().equals(kid) && key.alg().equals(alg))
                .findAny()
                .orElseThrow(() -> new UnAuthorizedException(ErrorMessage.JWT_UNAUTHORIZED_EXCEPTION));
    }
}
