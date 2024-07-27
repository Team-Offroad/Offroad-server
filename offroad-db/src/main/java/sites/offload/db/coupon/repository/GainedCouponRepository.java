package sites.offload.db.coupon.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.coupon.entity.GainedCouponEntity;

public interface GainedCouponRepository extends CrudRepository<GainedCouponEntity, Long> {
}
