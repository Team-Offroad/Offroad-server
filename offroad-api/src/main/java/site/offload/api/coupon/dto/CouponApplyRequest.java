package site.offload.api.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CouponApplyRequest(
        @Schema(description = "쿠폰 코드", example = "couponCode")
        String code,
        @Schema(description = "쿠폰 ID", example = "1")
        long couponId) {
}
