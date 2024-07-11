package site.offload.offloadserver.api.place.dto.response;

import java.util.List;

public record RegisteredPlacesResponse(
        List<RegisteredPlaceResponse> places
) {
    public static RegisteredPlacesResponse of(List<RegisteredPlaceResponse> places) {
        return new RegisteredPlacesResponse(places);
    }
}
