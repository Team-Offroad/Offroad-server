package site.offload.api.coupon.dto;

public record AvailableCouponResponse(long id, String name, String couponImageUrl, String description,
                                      boolean isNewGained) {

    public static AvailableCouponResponse of(long id, String name, String couponImageUrl, String description, boolean isNewGained) {
        return new AvailableCouponResponse(id, name, couponImageUrl, description, isNewGained);
    }
}
