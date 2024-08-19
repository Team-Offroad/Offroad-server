package site.offload.api.place.dto.response;

import site.offload.enums.place.PlaceCategory;

public record UnvisitedPlaceResponse(
        Long id,
        String name,
        String address,
        String shortIntroduction,
        PlaceCategory placeCategory,
        double latitude,
        double longitude,
        Long visitCount,
        String categoryImageUrl
) {
    public static UnvisitedPlaceResponse of(
            Long id,
            String name,
            String address,
            String shortIntroduction,
            PlaceCategory placeCategory,
            double latitude,
            double longitude,
            Long visitCount,
            String categoryImageUrl
    ) {
        return new UnvisitedPlaceResponse(id, name, address, shortIntroduction, placeCategory, latitude, longitude, visitCount, categoryImageUrl);
    }
}
