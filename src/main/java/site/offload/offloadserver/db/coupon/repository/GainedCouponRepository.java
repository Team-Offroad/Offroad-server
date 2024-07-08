package site.offload.offloadserver.db.coupon.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.coupon.entity.GainedCoupon;

public interface GainedCouponRepository extends CrudRepository<GainedCoupon, Long> {
}
