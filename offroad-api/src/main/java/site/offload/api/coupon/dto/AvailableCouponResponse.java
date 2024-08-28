package site.offload.api.coupon.dto;

public record AvailableCouponResponse(long id, String name, String couponImageUrl, String description,
                                      boolean isNewGained, long placeId) {

    public static AvailableCouponResponse of(long id, String name, String couponImageUrl, String description, boolean isNewGained, long placeId) {
        return new AvailableCouponResponse(id, name, couponImageUrl, description, isNewGained, placeId);
    }
}
