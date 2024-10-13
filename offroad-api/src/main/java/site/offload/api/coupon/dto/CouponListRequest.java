package site.offload.api.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CouponListRequest(
        @Schema(description = "페이지네이션 사이즈", example = "1")
        int size,
        @Schema(description = "쿠폰 목록 종류 구분을 위한 값, 사용가능은 true / 이미사용한 것은 false", example = "true")
        boolean isUsed,
        @Schema(description = "서버 요청 마지막 객체 cursorId 값", example = "2")
        long cursor) {

    public static CouponListRequest of(int size, boolean isUsed, long cursor) {
        return new CouponListRequest(size, isUsed, cursor);
    }
}
