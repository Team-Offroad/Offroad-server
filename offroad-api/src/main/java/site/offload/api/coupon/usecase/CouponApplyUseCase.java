package site.offload.api.coupon.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.coupon.dto.CouponApplyRequest;
import site.offload.api.coupon.dto.CouponApplyResponse;
import site.offload.api.coupon.service.CouponService;
import site.offload.api.coupon.service.GainedCouponService;
import site.offload.api.place.service.PlaceService;
import site.offload.api.quest.service.QuestRewardService;
import site.offload.api.quest.service.QuestService;
import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;
import site.offload.db.place.entity.PlaceEntity;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.db.quest.entity.QuestRewardEntity;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CouponApplyUseCase {

    private final PlaceService placeService;
    private final CouponService couponService;
    private final QuestService questService;
    private final QuestRewardService questRewardService;
    private final GainedCouponService gainedCouponService;

    @Transactional
    public CouponApplyResponse applyCoupon(final long memberId, final CouponApplyRequest request) {
        if (!gainedCouponService.isExistByMemberEntityIdAndCouponId(memberId, request.couponId())) {
            return CouponApplyResponse.of(false);
        }

        final GainedCouponEntity findGainedCouponEntity = gainedCouponService.findByMemberEntityIdAndCouponId(memberId, request.couponId());
        if (findGainedCouponEntity.isUsed()) {
            return CouponApplyResponse.of(false);
        }

        if (findGainedCouponEntity.getSamePlaceRewardPlaceId() != null && !findGainedCouponEntity.getSamePlaceRewardPlaceId().equals(request.placeId())) {
            return CouponApplyResponse.of(false);
        }

        final PlaceEntity findPlaceEntity = placeService.findByCouponAuthCode(request.code());
        if (findPlaceEntity.getCouponAuthCode() == null || !findPlaceEntity.getCouponAuthCode().equals(request.code())) {
            return CouponApplyResponse.of(false);
        }

        final CouponEntity findCouponEntity = couponService.findById(request.couponId());
        final QuestRewardEntity findQuestRewardEntity = questRewardService.findByCouponCode(findCouponEntity.getCouponCode());
        final QuestEntity findQuestEntity = questService.findById(findQuestRewardEntity.getQuestId());


        if (findQuestEntity.getPlaceArea() != PlaceArea.NONE && isValidPlaceArea(findQuestEntity, findPlaceEntity)) {
            handleValidCouponApplyRequest(findGainedCouponEntity);
            return CouponApplyResponse.of(true);
        }

        if (findQuestEntity.getPlaceCategory() != PlaceCategory.NONE && isValidPlaceCategory(findQuestEntity, findPlaceEntity)) {
            handleValidCouponApplyRequest(findGainedCouponEntity);
            return CouponApplyResponse.of(true);
        }

        if (findQuestEntity.getPlaceArea() == PlaceArea.NONE && findQuestEntity.getPlaceCategory() == PlaceCategory.NONE && findQuestEntity.isQuestSamePlace()) {
            if (isSamePlace(findPlaceEntity, findGainedCouponEntity)) {
                handleValidCouponApplyRequest(findGainedCouponEntity);
                return CouponApplyResponse.of(true);
            }
        }

        return CouponApplyResponse.of(false);
    }

    private void handleValidCouponApplyRequest(final GainedCouponEntity findGainedCouponEntity) {
        findGainedCouponEntity.updateIsUsed(true);
    }

    private boolean isValidPlaceArea(final QuestEntity findQuestEntity, final PlaceEntity findPlaceEntity) {
        return findPlaceEntity.getPlaceArea() == findQuestEntity.getPlaceArea();
    }

    private boolean isValidPlaceCategory(final QuestEntity findQuestEntity, final PlaceEntity findPlaceEntity) {
        return findPlaceEntity.getPlaceCategory() == findQuestEntity.getPlaceCategory();
    }

    private boolean isSamePlace(final PlaceEntity findPlaceEntity, final GainedCouponEntity findGainedCouponEntity) {
        return Objects.equals(findPlaceEntity.getId(), findGainedCouponEntity.getSamePlaceRewardPlaceId());
    }
}
