package site.offload.api.place.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record RegisteredPlacesResponse(
        @Schema(description = "등록된 장소 목록")
        List<RegisteredPlaceResponse> places
) {
    public static RegisteredPlacesResponse of(List<RegisteredPlaceResponse> places) {
        return new RegisteredPlacesResponse(places);
    }
}
