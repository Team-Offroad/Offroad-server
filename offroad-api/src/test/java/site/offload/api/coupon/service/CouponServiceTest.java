package site.offload.api.coupon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.fixture.CouponEntityFixtureCreator;
import site.offload.dbjpa.coupon.entity.CouponEntity;
import site.offload.dbjpa.coupon.repository.CouponRepository;

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
        CouponEntity coupon = CouponEntityFixtureCreator.createCoupon("testCoupon", "testImageUrl", "testDescription", "testCouponCode");
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

    @Test
    @DisplayName("쿠폰 id로 쿠폰을 조회할 수 있다.")
    void findByCouponId() {

        //given
        CouponEntity coupon = CouponEntityFixtureCreator.createCoupon("testCoupon", "testImageUrl", "testDescription", "testCouponCode");
        given(couponRepository.findById(anyLong())).willReturn(Optional.ofNullable(coupon));

        //when
        CouponEntity expectedCoupon = couponService.findById(1L);

        //then
        assertThat(coupon).isEqualTo(expectedCoupon);
    }
}