package site.offload.api.place.dto.request;

public record UnvisitedPlacesRequest(
        double currentLatitude,
        double currentLongitude) {
    public static UnvisitedPlacesRequest of(
            double currentLatitude,
            double currentLongitude
    ) {
        return new UnvisitedPlacesRequest(currentLatitude, currentLongitude);
    }
}
