package site.offload.api.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import site.offload.api.coupon.dto.CouponListResponse;
import site.offload.api.response.APISuccessResponse;

public interface CouponControllerSwagger {

    @Operation(summary = "획득 쿠폰 목록 조회 API", description = "사용한 쿠폰, 사용된 쿠폰 반환하는 API")
    @ApiResponse(responseCode = "200", description = "획득 쿠폰 조회 요청 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    ResponseEntity<APISuccessResponse<CouponListResponse>> getCouponList();
}
