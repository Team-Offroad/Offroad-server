package site.offload.offloadserver.external.oauth.google.request;

public record GoogleAuthRequest(
        String code,
        String clientId,
        String clientSecret,
        String redirectUri,
        String grantType
) {
    public static GoogleAuthRequest of(String code, String clientId, String clientSecret, String redirectUri, String grantType) {
        return new GoogleAuthRequest(code, clientId, clientSecret, redirectUri, grantType);
    }
}
