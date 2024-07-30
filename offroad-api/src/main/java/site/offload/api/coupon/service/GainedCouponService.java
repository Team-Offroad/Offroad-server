package site.offload.api.coupon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.db.coupon.repository.GainedCouponRepository;

@Service
@RequiredArgsConstructor
public class GainedCouponService {

    private final GainedCouponRepository gainedCouponRepository;

    public void deleteAllByMemberId(long memberId) {
        gainedCouponRepository.deleteAllByMemberEntityId(memberId);
    }
}
