package site.offload.api.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.coupon.dto.CouponApplyRequest;
import site.offload.api.coupon.dto.CouponApplyResponse;
import site.offload.api.coupon.dto.CouponListResponse;
import site.offload.api.response.APIErrorResponse;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@Tag(name = "[Coupon API] 쿠폰 관련 API")
public interface CouponControllerSwagger {

    @Operation(summary = "획득 쿠폰 목록 조회 API", description = "사용한 쿠폰, 사용된 쿠폰 반환하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "쿠폰 목록 조회 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<CouponListResponse>> getCouponList();

    @Operation(summary = "쿠폰 적용 API", description = "쿠폰 적용 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 적용 성공"),
            @ApiResponse(responseCode = "400", description = "쿠폰 적용 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<CouponApplyResponse>> useCoupon(CouponApplyRequest request);
}
