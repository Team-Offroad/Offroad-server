package site.offload.api.coupon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.repository.CouponRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @InjectMocks
    CouponService couponService;

    @Mock
    CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰 코드로 쿠폰을 조회할 수 있다.")
    void findByCouponCode() {
        //given
        CouponEntity coupon = createCoupon("testCoupon", "testImageUrl", "testDescription", "testCouponCode");
        given(couponRepository.findByCouponCode(anyString())).willReturn(Optional.ofNullable(coupon));

        //when
        CouponEntity expectedCoupon = couponService.findByCouponCode(coupon.getCouponCode());

        //then
        assertThat(expectedCoupon).extracting(
                "name",
                "couponImageUrl",
                "description",
                "couponCode"
        ).containsExactly(
                "testCoupon",
                "testImageUrl",
                "testDescription",
                "testCouponCode"
        );
    }

    private CouponEntity createCoupon(String name, String couponImageUrl, String description, String couponCode) {
        return CouponEntity.builder()
                .couponImageUrl(couponImageUrl)
                .description(description)
                .couponCode(couponCode)
                .name(name)
                .build();
    }
}