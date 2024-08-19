package site.offload.api.fixture;

import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;
import site.offload.db.emblem.entity.GainedEmblemEntity;
import site.offload.db.member.entity.MemberEntity;

public class GainedEmblemEntityFixtureCreator {

    public static GainedEmblemEntity createGainedEmblemEntity(MemberEntity memberEntity, String emblemCode) {
        return GainedEmblemEntity.builder()
                .memberEntity(memberEntity)
                .emblemCode(emblemCode)
                .build();
    }
}
