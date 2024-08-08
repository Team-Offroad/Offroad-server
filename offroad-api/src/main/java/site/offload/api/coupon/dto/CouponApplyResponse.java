package site.offload.api.coupon.dto;

public record CouponApplyResponse(boolean success) {

    public static CouponApplyResponse of(boolean success) {
        return new CouponApplyResponse(success);
    }
}
