package site.offload.api.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LogoutRequest(
    @Schema(description = "Refresh Token", example = "refreshToken")
    String refreshToken
) {
}
