package site.offload.api.coupon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CouponResponse(@Schema(description = "쿠폰 ID", example = "1")
                             Long id,
                             @Schema(description = "쿠폰 이름", example = "쿠폰 이름")
                             String name,
                             @Schema(description = "쿠폰 이미지 URL", example = "https://www.google.com")
                             String couponImageUrl,
                             @Schema(description = "쿠폰 설명", example = "쿠폰 설명")
                             String description,
                             @Schema(description = "신규 획득 여부", example = "true")
                             Boolean isNewGained,
                             @Schema(description = "서버에 무한스크롤 요청 시 사용되는 id", example = "1")
                             long cursorId) {

    public static CouponResponse of(Long id, String name, String couponImageUrl, String description, Boolean isNewGained, long cursorId) {
        return new CouponResponse(id, name, couponImageUrl, description, isNewGained, cursorId);
    }
}

