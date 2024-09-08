package site.offload.api.place.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UnvisitedPlacesRequest(
        @Schema(description = "현재 위도", example = "37.123456")
        double currentLatitude,
        @Schema(description = "현재 경도", example = "127.123456")
        double currentLongitude
) {
    public static UnvisitedPlacesRequest of(
            double currentLatitude,
            double currentLongitude
    ) {
        return new UnvisitedPlacesRequest(currentLatitude, currentLongitude);
    }
}
