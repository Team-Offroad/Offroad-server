package site.offload.api.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthAdventureRequest(
        @Schema(description = "장소 ID", example = "1")
        Long placeId,
        @Schema(description = "장소 이름", example = "테스트 장소")
        double longitude,
        @Schema(description = "장소 위도", example = "37.123456")
        double latitude,
        @Schema(description = "QR 코드", example = "https://test.com")
        String qrCode
) {
}
