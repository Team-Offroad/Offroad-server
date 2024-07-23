package sites.offload.db.coupon.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.coupon.entity.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long> {
}
