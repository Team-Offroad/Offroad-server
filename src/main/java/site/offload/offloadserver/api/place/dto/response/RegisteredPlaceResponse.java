package site.offload.offloadserver.api.place.dto.response;

import site.offload.offloadserver.db.place.entity.PlaceCategory;

public record RegisteredPlaceResponse(
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
    public static RegisteredPlaceResponse of(Long id, String name, String address, String shortIntroduction, PlaceCategory placeCategory, double latitude, double longitude, Long visitCount, String categoryImageUrl) {
        return new RegisteredPlaceResponse(id, name, address, shortIntroduction, placeCategory, latitude, longitude, visitCount, categoryImageUrl);
    }
}
