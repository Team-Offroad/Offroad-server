package site.offload.api.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.coupon.dto.CouponApplyRequest;
import site.offload.api.coupon.dto.CouponApplyResponse;
import site.offload.api.coupon.dto.CouponListRequest;
import site.offload.api.coupon.dto.CouponListResponse;
import site.offload.api.coupon.usecase.CouponApplyUseCase;
import site.offload.api.coupon.usecase.CouponListUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CouponController implements CouponControllerSwagger {

    private final CouponListUseCase couponListUseCase;
    private final CouponApplyUseCase couponApplyUseCase;

    @GetMapping("/users/coupons")
    public ResponseEntity<APISuccessResponse<CouponListResponse>> getCouponList(@RequestParam boolean isUsed,
                                                                                @RequestParam int size,
                                                                                @RequestParam int cursor) {
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.GET_COUPON_LIST_SUCCESS.getMessage(),
                couponListUseCase.getCouponList(CouponListRequest.of(size, isUsed, cursor), PrincipalHandler.getMemberIdFromPrincipal()));
    }

    @PostMapping("/users/coupons")
    public ResponseEntity<APISuccessResponse<CouponApplyResponse>> useCoupon(@RequestBody CouponApplyRequest request) {
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_COUPON_APPLY_SUCCESS.getMessage(),
                couponApplyUseCase.applyCoupon(PrincipalHandler.getMemberIdFromPrincipal(), request));
    }
}
