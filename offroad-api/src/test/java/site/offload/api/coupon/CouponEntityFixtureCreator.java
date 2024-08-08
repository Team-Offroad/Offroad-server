package site.offload.api.coupon;

import site.offload.db.coupon.entity.CouponEntity;

public class CouponEntityFixtureCreator {

    public static CouponEntity createCoupon(String name, String couponImageUrl, String description, String couponCode) {
        return CouponEntity.builder()
                .couponImageUrl(couponImageUrl)
                .description(description)
                .couponCode(couponCode)
                .name(name)
                .build();
    }
}
