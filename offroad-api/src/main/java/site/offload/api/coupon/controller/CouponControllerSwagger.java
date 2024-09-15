package site.offload.api.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.coupon.dto.CouponApplyRequest;
import site.offload.api.coupon.dto.CouponApplyResponse;
import site.offload.api.coupon.dto.CouponListResponse;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@Tag(name = "Coupon API", description = "쿠폰 관련 API")
public interface CouponControllerSwagger {

    @Operation(summary = "획득 쿠폰 목록 조회 API", description = "사용한 쿠폰과 사용된 쿠폰을 반환하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "획득 쿠폰 목록 조회 성공")
    ResponseEntity<APISuccessResponse<CouponListResponse>> getCouponList();

    @Operation(summary = "쿠폰 사용 API", description = "쿠폰을 사용하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "쿠폰 사용 성공")
    public ResponseEntity<APISuccessResponse<CouponApplyResponse>> useCoupon(CouponApplyRequest request);
}
