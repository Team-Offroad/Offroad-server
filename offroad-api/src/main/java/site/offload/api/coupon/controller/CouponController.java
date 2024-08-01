package site.offload.api.coupon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.coupon.dto.CouponListResponse;
import site.offload.api.coupon.usecase.CouponListUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CouponController implements CouponControllerSwagger {

    private final CouponListUseCase couponListUseCase;

    @GetMapping("/users/coupons")
    public ResponseEntity<APISuccessResponse<CouponListResponse>> getCouponList() {
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.GET_COUPON_LIST_SUCCESS.getMessage(),
                couponListUseCase.getCouponList(PrincipalHandler.getMemberIdFromPrincipal()));
    }
}
