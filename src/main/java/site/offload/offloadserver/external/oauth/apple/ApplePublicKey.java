package site.offload.offloadserver.external.oauth.apple;

public record ApplePublicKey(
        String kty,
        String kid,
        String alg,
        String n,
        String e
) {
}
