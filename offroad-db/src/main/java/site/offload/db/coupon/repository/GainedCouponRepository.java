package site.offload.db.coupon.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.coupon.entity.GainedCouponEntity;

import java.util.List;

public interface GainedCouponRepository extends JpaRepository<GainedCouponEntity, Long> {

    void deleteAllByMemberEntityId(long memberId);

    List<GainedCouponEntity> findAllByMemberEntityIdOrderByCreatedAtDesc(long memberId);
}
