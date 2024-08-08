package site.offload.api.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.exception.NotFoundException;
import site.offload.db.coupon.entity.GainedCouponEntity;
import site.offload.db.coupon.repository.GainedCouponRepository;
import site.offload.enums.response.ErrorMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GainedCouponService {

    private final GainedCouponRepository gainedCouponRepository;

    public void deleteAllByMemberId(long memberId) {
        gainedCouponRepository.deleteAllByMemberEntityId(memberId);
    }

    public List<GainedCouponEntity> findAllByMemberEntityIdOrderByCreatedAtDesc(long memberId) {
        return gainedCouponRepository.findAllByMemberEntityIdOrderByCreatedAtDesc(memberId);
    }

    public boolean isExistByMemberEntityIdAndCouponId(long memberId, long couponId) {

        return gainedCouponRepository.existsByMemberEntityIdAndCouponEntityId(memberId, couponId);
    }

    public void save(GainedCouponEntity gainedCouponEntity) {
        gainedCouponRepository.save(gainedCouponEntity);
    }

    public GainedCouponEntity findByMemberEntityIdAndCouponId(long memberId, long couponId) {
        return gainedCouponRepository.findByMemberEntityIdAndCouponEntityId(memberId, couponId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.GAINED_COUPON_NOTFOUND_EXCEPTION)
        );
    }
}
