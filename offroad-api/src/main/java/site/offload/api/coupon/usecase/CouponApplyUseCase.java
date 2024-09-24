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

        if (!isValidCouponUseConditions(memberId, request)) {
            return CouponApplyResponse.of(false);
        }

        final CouponEntity findCouponEntity = couponService.findById(request.couponId());
        final QuestRewardEntity findQuestRewardEntity = questRewardService.findByCouponCode(findCouponEntity.getCouponCode());
        final QuestEntity findQuestEntity = questService.findById(findQuestRewardEntity.getQuestId());
        final GainedCouponEntity findGainedCouponEntity = gainedCouponService.findByMemberEntityIdAndCouponId(memberId, request.couponId());
        final PlaceEntity findPlaceEntity = placeService.findByCouponAuthCode(request.code());

        // ~구역 쿠폰을 사용할 때 입력받은 장소의 구역과 비교
        if (findQuestEntity.getPlaceArea() != PlaceArea.NONE && isValidPlaceArea(findQuestEntity, findPlaceEntity)) {
            handleValidCouponApplyRequest(findGainedCouponEntity);
            return CouponApplyResponse.of(true);
        }

        // 장소 카테고리 쿠폰 (ex: 카페 10만원 권 등)을 사용할 때 입력받은 장소의 카테고리와 비교
        if (findQuestEntity.getPlaceCategory() != PlaceCategory.NONE && isValidPlaceCategory(findQuestEntity, findPlaceEntity)) {
            handleValidCouponApplyRequest(findGainedCouponEntity);
            return CouponApplyResponse.of(true);
        }

        // 해당 장소 ~만원 , 해당 장소 이용권 N매 쿠폰 사용시 입력받은 장소 id를 비교
        if (findQuestEntity.getPlaceArea() == PlaceArea.NONE && findQuestEntity.getPlaceCategory() == PlaceCategory.NONE && findQuestEntity.isQuestSamePlace()) {
            if (isSamePlace(findPlaceEntity, findGainedCouponEntity)) {
                handleValidCouponApplyRequest(findGainedCouponEntity);
                return CouponApplyResponse.of(true);
            }
        }

        return CouponApplyResponse.of(false);
    }

    private boolean isValidCouponUseConditions(long memberId, CouponApplyRequest request) {

        // 사용자가 획득한 쿠폰이 아니면 인증 실패
        if (!gainedCouponService.isExistByMemberEntityIdAndCouponId(memberId, request.couponId())) {
            return false;
        }

        // 이미 사용된 쿠폰이면 인증 실패
        final GainedCouponEntity findGainedCouponEntity = gainedCouponService.findByMemberEntityIdAndCouponId(memberId, request.couponId());
        if (findGainedCouponEntity.isUsed()) {
            return false;
        }

        // 쿠폰 코드에 해당하는 장소가 없으면 인증 실패
        if(!placeService.isExistByCouponAuthCode(request.code())) {
            return false;
        }

        return true;
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
