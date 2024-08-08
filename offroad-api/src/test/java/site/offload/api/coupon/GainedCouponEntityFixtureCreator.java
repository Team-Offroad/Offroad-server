package site.offload.api.coupon;

import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;
import site.offload.db.member.entity.MemberEntity;

public class GainedCouponEntityFixtureCreator {

    public static GainedCouponEntity createGainedCouponEntity(MemberEntity memberEntity, CouponEntity couponEntity) {
        return GainedCouponEntity.builder()
                .memberEntity(memberEntity)
                .couponEntity(couponEntity)
                .build();
    }
}
