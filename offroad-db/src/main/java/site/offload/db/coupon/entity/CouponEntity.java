package site.offload.db.coupon.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.db.BaseTimeEntity;

//보상으로 얻을 수 있는 쿠폰
@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true)
    private String couponCode;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String couponImageUrl;

    @Builder
    public CouponEntity(String name, String description, String couponCode, String couponImageUrl) {
        this.name = name;
        this.description = description;
        this.couponCode = couponCode;
        this.couponImageUrl = couponImageUrl;

    }
}
