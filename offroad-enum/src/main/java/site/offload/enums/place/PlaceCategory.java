package site.offload.enums.place;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

//장소 카테고리
@RequiredArgsConstructor
@Getter
public enum PlaceCategory {
    CAFFE("카페"),
    PARK("공원"),
    RESTAURANT("식당"),
    CULTURE("문화"),
    SPORT("스포츠"),
    NONE("카테고리 없음");

    private final String placeCategoryName;

    public static boolean isExistsCategory(final String placeCategory) {
        for (PlaceCategory category : PlaceCategory.values()) {
            if (category.name().equals(placeCategory.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public static List<PlaceCategory> nearBy100meterPlaceCategory() {
        return List.of(PlaceCategory.PARK, PlaceCategory.SPORT);
    }

    public static List<PlaceCategory> nearBy25meterPlaceCategory() {
        return List.of(PlaceCategory.RESTAURANT, PlaceCategory.CAFFE, PlaceCategory.CULTURE);
    }

    public static boolean isPlaceCategoryForTicketCoupon(final PlaceCategory placeCategory) {
        return placeCategory == CULTURE || placeCategory == SPORT;
    }

    public static boolean isPlaceCategoryForFixedDiscountCoupon(final PlaceCategory placeCategory) {
        return placeCategory == RESTAURANT || placeCategory == CAFFE;
    }
}
