package site.offload.api.fixture;

import site.offload.db.charactermotion.entity.CharacterMotionEntity;
import site.offload.db.charactermotion.entity.GainedCharacterMotionEntity;
import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;
import site.offload.db.member.entity.MemberEntity;

public class GainedCharacterMotionEntityFixtureCreator {

    public static GainedCharacterMotionEntity createGainedCharacterMotionEntity(MemberEntity memberEntity, CharacterMotionEntity characterMotionEntity) {
        return GainedCharacterMotionEntity.builder()
                .memberEntity(memberEntity)
                .characterMotionEntity(characterMotionEntity)
                .build();
    }
}
