package site.offload.api.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AvailableCouponResponse(
        @Schema(description = "쿠폰 ID", example = "1")

        long id,
        @Schema(description = "쿠폰 이름", example = "쿠폰 이름")
        String name,
        @Schema(description = "쿠폰 이미지 URL", example = "https://www.google.com")
        String couponImageUrl,
        @Schema(description = "쿠폰 설명", example = "쿠폰 설명")
        String description,
        @Schema(description = "신규 획득 여부", example = "true")
        boolean isNewGained
) {

    public static AvailableCouponResponse of(long id, String name, String couponImageUrl, String description, boolean isNewGained) {
        return new AvailableCouponResponse(id, name, couponImageUrl, description, isNewGained);
    }
}
