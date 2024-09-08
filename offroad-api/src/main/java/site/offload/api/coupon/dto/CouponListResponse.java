package site.offload.api.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CouponListResponse(
        @Schema(description = "사용 가능한 쿠폰 목록")
        List<AvailableCouponResponse> availableCoupons,
        @Schema(description = "사용한 쿠폰 목록")
        List<UsedCouponResponse> usedCoupons
) {

    public static CouponListResponse of(List<AvailableCouponResponse> availableCoupons, List<UsedCouponResponse> usedCoupons) {
        return new CouponListResponse(availableCoupons, usedCoupons);
    }
}
