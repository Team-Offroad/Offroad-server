package site.offload.api.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.api.exception.NotFoundException;
import site.offload.dbjpa.coupon.entity.CouponEntity;
import site.offload.dbjpa.coupon.repository.CouponRepository;
import site.offload.enums.response.ErrorMessage;

@Component
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponEntity findByCouponCode(String couponCode) {

        return couponRepository.findByCouponCode(couponCode).orElseThrow(
                () -> new NotFoundException(ErrorMessage.COUPON_NOTFOUND_EXCEPTION)
        );
    }

    public CouponEntity findById(Long id) {
        return couponRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.COUPON_NOTFOUND_EXCEPTION)
        );
    }
}
