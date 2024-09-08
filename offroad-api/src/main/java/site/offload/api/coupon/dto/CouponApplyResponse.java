package site.offload.api.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CouponApplyResponse(
        @Schema(description = "쿠폰 적용 성공 여부", example = "true")
        boolean success
) {

    public static CouponApplyResponse of(boolean success) {
        return new CouponApplyResponse(success);
    }
}
