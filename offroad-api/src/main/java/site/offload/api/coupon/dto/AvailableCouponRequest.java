package site.offload.api.coupon.dto;

public record AvailableCouponRequest(long id, String name, String couponImageUrl, String description) {

    public static AvailableCouponRequest of(long id, String name, String couponImageUrl, String description) {
        return new AvailableCouponRequest(id, name, couponImageUrl, description);
    }
}
