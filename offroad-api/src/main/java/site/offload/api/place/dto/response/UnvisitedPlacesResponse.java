package site.offload.api.place.dto.response;

import java.util.List;

public record UnvisitedPlacesResponse(
        List<UnvisitedPlaceResponse> places
) {
    public static UnvisitedPlacesResponse of(List<UnvisitedPlaceResponse> places) {
        return new UnvisitedPlacesResponse(places);
    }
}
