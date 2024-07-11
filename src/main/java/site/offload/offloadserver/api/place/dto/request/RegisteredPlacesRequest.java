package site.offload.offloadserver.api.place.dto.request;

public record RegisteredPlacesRequest(
        double currentLatitude,
        double currentLongitude
) {
    public static RegisteredPlacesRequest of(double currentLatitude, double currentLongitude) {
        return new RegisteredPlacesRequest(currentLatitude, currentLongitude);
    }
}
