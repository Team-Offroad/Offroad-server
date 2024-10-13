package site.offload.api.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CouponListResponse(
        @Schema(description = "쿠폰 목록")
        List<CouponResponse> coupons
) {

    public static CouponListResponse of(List<CouponResponse> coupons) {
        return new CouponListResponse(coupons);
    }
}


