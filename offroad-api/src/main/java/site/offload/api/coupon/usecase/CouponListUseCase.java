package site.offload.api.coupon.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.coupon.dto.CouponListRequest;
import site.offload.api.coupon.dto.CouponListResponse;
import site.offload.api.coupon.dto.CouponResponse;
import site.offload.api.coupon.service.GainedCouponService;
import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponListUseCase {

    private final GainedCouponService gainedCouponService;

    // 쿠폰 목록은 획득 날짜의 내림차순으로 반환
    @Transactional
    public CouponListResponse getCouponList(final CouponListRequest request, final long memberId) {
        return CouponListResponse.of(getCouponsPaginated(request, memberId));
    }

    private List<CouponResponse> getCouponsPaginated(final CouponListRequest request, final long memberId) {
        Pageable pageable = PageRequest.of(0, request.size());

        final List<GainedCouponEntity> paginatedGainedCoupons = getGainedCouponsPaginatedByIdDesc(request, memberId, pageable);

        return mapToCouponResponse(request, paginatedGainedCoupons);
    }

    private List<GainedCouponEntity> getGainedCouponsPaginatedByIdDesc(final CouponListRequest request, final long memberId, final Pageable pageable) {
        long cursor = calculateCursor(request);

        return gainedCouponService.findByMemberEntityIdAndIdLessThanAndUsedOrderByIdDesc(
                memberId, cursor, request.isUsed(), pageable);
    }

    private long calculateCursor(final CouponListRequest request) {
        return request.cursor() == 0 ? Long.MAX_VALUE : request.cursor();
    }

    private List<CouponResponse> mapToCouponResponse(final CouponListRequest request, final List<GainedCouponEntity> paginatedGainedCoupons) {
        return paginatedGainedCoupons.stream()
                .map(gainedCouponEntity -> getCouponResponse(gainedCouponEntity, request.isUsed()))
                .toList();
    }

    private CouponResponse getCouponResponse(final GainedCouponEntity gainedCouponEntity, final boolean isUsed) {
        return isUsed ? getUsedCouponResponse(gainedCouponEntity) : getAvailableCouponResponse(gainedCouponEntity);
    }

    private CouponResponse getUsedCouponResponse(final GainedCouponEntity gainedCouponEntity) {
        final CouponEntity couponEntity = gainedCouponEntity.getCouponEntity();

        return CouponResponse.of(
                null,
                couponEntity.getName(),
                couponEntity.getCouponImageUrl(),
                null,
                null,
                gainedCouponEntity.getId()
        );
    }

    private CouponResponse getAvailableCouponResponse(final GainedCouponEntity gainedCouponEntity) {
        final CouponEntity couponEntity = gainedCouponEntity.getCouponEntity();
        boolean isNewGained = gainedCouponEntity.isNewGained();
        updateNewGainedStatus(gainedCouponEntity);

        return CouponResponse.of(
                couponEntity.getId(),
                couponEntity.getName(),
                couponEntity.getCouponImageUrl(),
                couponEntity.getDescription(),
                isNewGained,
                gainedCouponEntity.getId()
        );
    }

    private void updateNewGainedStatus(final GainedCouponEntity gainedCouponEntity) {
        gainedCouponEntity.updateNewGainedStatus();
    }
}
