package site.offload.api.coupon.dto;

import java.util.List;

public record CouponListResponse(List<AvailableCouponRequest> availableCoupons, List<UsedCouponRequest> usedCoupons) {

    public static CouponListResponse of(List<AvailableCouponRequest> availableCoupons, List<UsedCouponRequest> usedCoupons) {
        return new CouponListResponse(availableCoupons, usedCoupons);
    }
}
