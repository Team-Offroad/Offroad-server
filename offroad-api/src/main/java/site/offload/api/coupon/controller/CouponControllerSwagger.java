package site.offload.api.coupon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.api.coupon.dto.CouponApplyRequest;
import site.offload.api.coupon.dto.CouponApplyResponse;
import site.offload.api.coupon.dto.CouponListResponse;
import site.offload.api.response.APIErrorResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "[Coupon API] 쿠폰 관련 API")
public interface CouponControllerSwagger {

    @Operation(summary = "획득 쿠폰 목록 조회 API", description = "사용한 쿠폰, 사용된 쿠폰 반환하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 성공"),
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<CouponListResponse>> getCouponList(@RequestParam boolean isUsed,
                                                                         @RequestParam int size,
                                                                         @RequestParam int cursor);

    @Operation(summary = "쿠폰 적용 API", description = "쿠폰 적용 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "쿠폰 사용 요청 성공, 응답 값에 있는 data의 success가 true인 경우 적용 성공, false라면 적용 실패."),

            @ApiResponse(responseCode = "404", description = "요청에 필요한 자원이 없음", content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                    examples = {
                            @ExampleObject(name = "존재하지 않는 쿠폰", value = "{ \"message\": \"존재하지 않는 쿠폰\", \"customErrorCode\": \"NOT_EXISTS_COUPON\" }"),
                            @ExampleObject(name = "존재하지 않는 장소", value = "{ \"message\": \"존재하지 않는 장소\", \"customErrorCode\": \"NOT_EXISTS_PLACE\" }", description = "쿠폰 코드에 해당하는 장소가 존재하지 않음"),

                    })),
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<CouponApplyResponse>> useCoupon(CouponApplyRequest request);
}
