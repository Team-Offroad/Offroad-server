package site.offload.api.place.dto.request;

public record RegisteredPlacesRequest(
        double currentLatitude,
        double currentLongitude,
        Boolean isVisit
) {
    public static RegisteredPlacesRequest of(
            double currentLatitude,
            double currentLongitude,
            Boolean isVisit) {
        return new RegisteredPlacesRequest(currentLatitude, currentLongitude, isVisit);
    }
}
