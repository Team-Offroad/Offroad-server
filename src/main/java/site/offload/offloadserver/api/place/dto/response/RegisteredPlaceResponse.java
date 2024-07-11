package site.offload.offloadserver.api.place.dto.response;

import site.offload.offloadserver.db.place.entity.PlaceCategory;

public record RegisteredPlaceResponse(
        Long id,
        String name,
        String address,
        String shortIntroduction,
        PlaceCategory placeCategory,
        float latitude,
        float longitude,
        int visitCount
) {
    public static RegisteredPlaceResponse of(Long id, String name, String address, String shortIntroduction, PlaceCategory placeCategory, float latitude, float longitude, int visitCount) {
        return new RegisteredPlaceResponse(id, name, address, shortIntroduction, placeCategory, latitude, longitude, visitCount);
    }
}
