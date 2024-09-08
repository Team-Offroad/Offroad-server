package site.offload.api.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UsedCouponResponse(
        @Schema(description = "쿠폰 이름", example = "쿠폰 이름")
        String name,
        @Schema(description = "쿠폰 이미지 URL", example = "https://coupon-image-url.com")
        String couponImageUrl
) {
    public static UsedCouponResponse of(String name, String couponImageUrl) {
        return new UsedCouponResponse(name, couponImageUrl);
    }
}
