package site.offload.offloadserver.db.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.entity.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long> {
}
