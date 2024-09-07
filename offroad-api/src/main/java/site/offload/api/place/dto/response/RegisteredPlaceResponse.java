package site.offload.api.place.dto.response;


import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

public record RegisteredPlaceResponse(
        Long id,
        String name,
        String address,
        String shortIntroduction,
        PlaceCategory placeCategory,
        PlaceArea placeArea,
        double latitude,
        double longitude,
        Long visitCount,
        String categoryImageUrl
) {
    public static RegisteredPlaceResponse of(
            Long id,
            String name,
            String address,
            String shortIntroduction,
            PlaceCategory placeCategory,
            PlaceArea placeArea,
            double latitude,
            double longitude,
            Long visitCount,
            String categoryImageUrl
    ) {
        return new RegisteredPlaceResponse(
                id,
                name,
                address,
                shortIntroduction,
                placeCategory,
                placeArea,
                latitude,
                longitude,
                visitCount,
                categoryImageUrl);
    }
}
