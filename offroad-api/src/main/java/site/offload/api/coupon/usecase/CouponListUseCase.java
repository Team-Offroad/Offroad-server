package site.offload.api.coupon.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.coupon.dto.AvailableCouponRequest;
import site.offload.api.coupon.dto.CouponListResponse;
import site.offload.api.coupon.dto.UsedCouponRequest;
import site.offload.api.coupon.service.GainedCouponService;
import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponListUseCase {

    private final GainedCouponService gainedCouponService;

    //쿠폰 목록은 획득 날짜의 내림차순으로 반환
    @Transactional(readOnly = true)
    public CouponListResponse getCouponList(final long memberId) {
        final List<GainedCouponEntity> findGainedCoupontList = gainedCouponService.findAllByMemberEntityIdOrderByCreatedAtDesc(memberId);
        return CouponListResponse.of(
                getAvailableCouponList(findGainedCoupontList), getUsedCouponList(findGainedCoupontList)
        );
    }

    private List<AvailableCouponRequest> getAvailableCouponList(final List<GainedCouponEntity> findGainedCoupontList) {
        List<AvailableCouponRequest> availableCouponList = new ArrayList<>();
        findGainedCoupontList.forEach(
                gainedCouponEntity -> {
                    if (!gainedCouponEntity.isUsed()) {
                        final CouponEntity couponEntity = gainedCouponEntity.getCouponEntity();
                        availableCouponList.add(
                                AvailableCouponRequest.of(
                                        couponEntity.getId(),
                                        couponEntity.getName(),
                                        couponEntity.getCouponImageUrl(),
                                        couponEntity.getDescription())
                        );
                    }
                }
        );
        return availableCouponList;
    }

    private List<UsedCouponRequest> getUsedCouponList(final List<GainedCouponEntity> findGainedCoupontList) {
        List<UsedCouponRequest> usedCouponList = new ArrayList<>();
        findGainedCoupontList.forEach(
                gainedCouponEntity -> {
                    if (gainedCouponEntity.isUsed()) {
                        final CouponEntity couponEntity = gainedCouponEntity.getCouponEntity();
                        usedCouponList.add(
                                UsedCouponRequest.of(
                                        couponEntity.getName(),
                                        couponEntity.getCouponImageUrl())
                        );
                    }
                }
        );
        return usedCouponList;
    }


}
