package site.offload.db.coupon.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.coupon.entity.GainedCouponEntity;

public interface GainedCouponRepository extends JpaRepository<GainedCouponEntity, Long> {
}
