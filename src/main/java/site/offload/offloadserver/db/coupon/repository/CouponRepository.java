package site.offload.offloadserver.db.coupon.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.coupon.entity.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long> {
}
