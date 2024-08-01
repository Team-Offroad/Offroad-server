package site.offload.api.coupon.dto;

public record AvailableCouponResponse(long id, String name, String couponImageUrl, String description) {

    public static AvailableCouponResponse of(long id, String name, String couponImageUrl, String description) {
        return new AvailableCouponResponse(id, name, couponImageUrl, description);
    }
}
