package site.offload.offloadserver.api.place.dto.request;

public record RegisteredPlacesRequest(
        float currentLatitude,
        float currentLongitude
) {
    public static RegisteredPlacesRequest of(float currentLatitude, float currentLongitude) {
        return new RegisteredPlacesRequest(currentLatitude, currentLongitude);
    }
}
