package site.offload.offloadserver.common.jwt;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
    public static TokenResponse of(String accessToken, String refreshToken) {
        return new TokenResponse("Bearer " + accessToken, "Bearer " + refreshToken);
    }
}
