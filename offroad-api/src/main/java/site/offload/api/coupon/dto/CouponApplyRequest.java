package site.offload.api.coupon.dto;

public record CouponApplyRequest(String code, long couponId, long placeId) {
}
