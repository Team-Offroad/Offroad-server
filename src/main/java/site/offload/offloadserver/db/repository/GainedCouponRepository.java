package site.offload.offloadserver.db.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.entity.GainedCoupon;

public interface GainedCouponRepository extends CrudRepository<GainedCoupon, Long> {
}
