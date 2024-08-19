package site.offload.db.coupon.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.db.BaseTimeEntity;
import site.offload.db.member.entity.MemberEntity;

//사용자가 얻은 쿠폰
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gained_coupon",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "coupon_id"})
})
public class GainedCouponEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private CouponEntity couponEntity;

    @Column(nullable = true)
    private Long samePlaceRewardPlaceId;

    private boolean isUsed = false;

    private boolean isNewGained = true;

    @Builder
    public GainedCouponEntity(MemberEntity memberEntity, CouponEntity couponEntity, Long samePlaceRewardPlaceId) {
        this.memberEntity = memberEntity;
        this.couponEntity = couponEntity;
        this.samePlaceRewardPlaceId = samePlaceRewardPlaceId;
    }

    public void updateIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public void updateNewGainedStatus() {
        this.isNewGained = false;
    }
}
