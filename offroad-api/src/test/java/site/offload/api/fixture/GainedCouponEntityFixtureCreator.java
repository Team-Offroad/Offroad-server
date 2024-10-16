package site.offload.api.fixture;

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

    public static GainedCouponEntity createGainedCouponEntity(MemberEntity memberEntity, CouponEntity couponEntity, long acquisitionPlaceId) {
        return GainedCouponEntity.builder()
                .memberEntity(memberEntity)
                .couponEntity(couponEntity)
                .acquisitionPlaceId(acquisitionPlaceId)
                .build();
    }
}
