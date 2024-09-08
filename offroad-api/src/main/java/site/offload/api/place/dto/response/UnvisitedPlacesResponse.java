package site.offload.api.place.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record UnvisitedPlacesResponse(
        @Schema(description = "방문하지 않은 장소 목록")
        List<UnvisitedPlaceResponse> places
) {
    public static UnvisitedPlacesResponse of(List<UnvisitedPlaceResponse> places) {
        return new UnvisitedPlacesResponse(places);
    }
}
