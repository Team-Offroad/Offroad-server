package site.offload.api.coupon.dto;

public record UsedCouponResponse(String name, String couponImageUrl) {
    public static UsedCouponResponse of(String name, String couponImageUrl) {
        return new UsedCouponResponse(name, couponImageUrl);
    }
}
