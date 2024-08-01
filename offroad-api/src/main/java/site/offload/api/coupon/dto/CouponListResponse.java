package site.offload.api.coupon.dto;

import java.util.List;

public record CouponListResponse(List<AvailableCouponResponse> availableCoupons, List<UsedCouponResponse> usedCoupons) {

    public static CouponListResponse of(List<AvailableCouponResponse> availableCoupons, List<UsedCouponResponse> usedCoupons) {
        return new CouponListResponse(availableCoupons, usedCoupons);
    }
}
