package site.offload.offloadserver.api.member.dto.response;

public record TokenReissueResponse(String accessToken, String refreshToken) {

    public static TokenReissueResponse of(final String accessToken, final String refreshToken) {
        return new TokenReissueResponse(accessToken, refreshToken);
    }
}
