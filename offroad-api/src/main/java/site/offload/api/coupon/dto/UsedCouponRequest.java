package site.offload.api.coupon.dto;

public record UsedCouponRequest(String name, String couponImageUrl) {
    public static UsedCouponRequest of(String name, String couponImageUrl) {
        return new UsedCouponRequest(name, couponImageUrl);
    }
}
