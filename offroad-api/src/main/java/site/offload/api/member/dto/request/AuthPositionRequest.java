package site.offload.api.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthPositionRequest(
        @Schema(description = "장소 ID", example = "1")
        Long placeId,
        @Schema(description = "위도", example = "37.123456")
        double latitude,
        @Schema(description = "경도", example = "127.123456")
        double longitude) {
}
