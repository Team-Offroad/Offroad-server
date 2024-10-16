package site.offload.api.fixture;

import site.offload.db.place.entity.PlaceEntity;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

public class PlaceEntityFixtureCreator {

    public static PlaceEntity createPlace(
            PlaceArea placeArea,
            PlaceCategory placeCategory,
            String name,
            String address,
            String shortIntroduction,
            String offroadCode,
            double latitude,
            double longitude,
            String categoryImageUrl,
            String couponAuthCode
    ) {
        return PlaceEntity.builder()
                .placeArea(placeArea)
                .placeCategory(placeCategory)
                .name(name)
                .address(address)
                .shortIntroduction(shortIntroduction)
                .offroadCode(offroadCode)
                .latitude(latitude)
                .longitude(longitude)
                .categoryImageUrl(categoryImageUrl)
                .couponAuthCode(couponAuthCode)
                .build();
    }
}
