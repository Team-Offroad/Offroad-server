package site.offload.offloadserver.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

//보상으로 얻을 수 있는 쿠폰
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseTime {

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
    private Coupon(String name, String couponCode, String description, String couponImageUrl) {
        this.name = name;
        this.couponCode = couponCode;
        this.description = description;
        this.couponImageUrl = couponImageUrl;
    }
}
