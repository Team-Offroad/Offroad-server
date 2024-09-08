package site.offload.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenReissueResponse(
        @Schema(description = "액세스 토큰", example = "accessToken")
        String accessToken,
        @Schema(description = "리프레시 토큰", example = "refreshToken")
        String refreshToken) {

    public static TokenReissueResponse of(final String accessToken, final String refreshToken) {
        return new TokenReissueResponse(accessToken, refreshToken);
    }
}
