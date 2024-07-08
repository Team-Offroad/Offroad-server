package site.offload.offloadserver.db.coupon.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.BaseTimeEntity;
import site.offload.offloadserver.db.member.entity.Member;

//사용자가 얻은 쿠폰
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class GainedCoupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    private boolean isUsed = false;
}
