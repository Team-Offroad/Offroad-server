package site.offload.db.coupon.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.coupon.entity.CouponEntity;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {
}
