package site.offload.offloadserver.db.place.entity;

//장소 카테고리
public enum PlaceCategory {
    CAFFE,
    PARK,
    RESTAURANT,
    CULTURE,
    SPORT,
    NONE;

    public static boolean isExistsCategory(final String placeCategory) {
        for (PlaceCategory category : PlaceCategory.values()) {
            if (category.name().equals(placeCategory.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
