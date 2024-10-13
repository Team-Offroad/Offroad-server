package site.offload.db.coupon.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.coupon.entity.GainedCouponEntity;

import java.util.List;
import java.util.Optional;

public interface GainedCouponRepository extends JpaRepository<GainedCouponEntity, Long> {

    void deleteAllByMemberEntityId(long memberId);

    List<GainedCouponEntity> findAllByMemberEntityIdOrderByCreatedAtDesc(long memberId);

    boolean existsByMemberEntityIdAndCouponEntityId(long memberId, long couponId);

    Optional<GainedCouponEntity> findByMemberEntityIdAndCouponEntityId(long memberId, long couponId);

    List<GainedCouponEntity> findByMemberEntityIdAndIdLessThanAndIsUsedOrderByIdDesc(
            long memberId, long cursorId, boolean isUsed, Pageable pageable);
}
