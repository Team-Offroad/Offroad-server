package site.offload.api.coupon.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.coupon.dto.CouponApplyRequest;
import site.offload.api.coupon.dto.CouponApplyResponse;
import site.offload.api.coupon.service.CouponService;
import site.offload.api.coupon.service.GainedCouponService;
import site.offload.api.place.service.PlaceService;
import site.offload.api.quest.service.QuestRewardService;
import site.offload.api.quest.service.QuestService;
import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.place.entity.PlaceEntity;
import site.offload.db.quest.embeddable.RewardList;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.db.quest.entity.QuestRewardEntity;
import site.offload.enums.member.SocialPlatform;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static site.offload.api.fixture.CouponEntityFixtureCreator.createCoupon;
import static site.offload.api.fixture.GainedCouponEntityFixtureCreator.createGainedCouponEntity;
import static site.offload.api.fixture.MemberEntityFixtureCreator.createMemberEntity;
import static site.offload.api.fixture.PlaceEntityFixtureCreator.createPlace;
import static site.offload.api.fixture.QuestEntityFixtureCreator.createQuest;
import static site.offload.api.fixture.QuestRewardFixtureCreator.createQuestReward;

@ExtendWith(MockitoExtension.class)
class CouponApplyUseCaseTest {

    @InjectMocks
    private CouponApplyUseCase couponApplyUseCase;

    @Mock
    private PlaceService placeService;
    @Mock
    private CouponService couponService;
    @Mock
    private QuestService questService;
    @Mock
    private QuestRewardService questRewardService;
    @Mock
    private GainedCouponService gainedCouponService;

    @Test
    @DisplayName("사용자는 획득하지 않은 쿠폰에 대한 쿠폰 사용 요청시 인증에 실패한다.")
    void failToApplyCouponWhenNotGainedCoupon() {
        //given
        CouponApplyRequest couponApplyRequest = new CouponApplyRequest("testcode", 1L, 1L);
        given(gainedCouponService.isExistByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(false);

        //when
        CouponApplyResponse actualResponse = couponApplyUseCase.applyCoupon(1L, couponApplyRequest);

        //then
        assertThat(actualResponse.success()).isFalse();
    }

    @Test
    @DisplayName("사용자는 쿠폰 사용가능 횟수를 모두 소모할 경우 쿠폰 사용 인증에 실패한다")
    void failWhenAvailableUseCountIsZero() {
        //given
        MemberEntity memberEntity = createMemberEntity("sub", "text@test.com", SocialPlatform.GOOGLE, "kim");
        CouponEntity couponEntity = createCoupon("name", "url", "description", "couponCode");
        CouponApplyRequest couponApplyRequest = new CouponApplyRequest("testcode", 1L, 1L);
        GainedCouponEntity gainedCouponEntity = createGainedCouponEntity(memberEntity, couponEntity);

        gainedCouponEntity.updateIsUsed(true);

        given(gainedCouponService.isExistByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(true);
        given(gainedCouponService.findByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(gainedCouponEntity);

        //when
        CouponApplyResponse actualResponse = couponApplyUseCase.applyCoupon(1L, couponApplyRequest);

        //then
        assertThat(actualResponse.success()).isFalse();
    }

    @Test
    @DisplayName("사용자는 유효하지 않은 쿠폰 사용 코드를 입력할 경우 쿠폰 사용 인증에 실패한다")
    void failWhenInvalidCouponAuthCode() {
        //given
        MemberEntity memberEntity = createMemberEntity("sub", "text@test.com", SocialPlatform.GOOGLE, "kim");
        CouponEntity couponEntity = createCoupon("name", "url", "description", "couponCode");
        PlaceEntity placeEntity = createPlace(PlaceArea.AREA1, PlaceCategory.CULTURE, "테스트이름", "테스트주소",
                "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL", "테스트쿠폰사용코드");

        // 유효하지 않은 쿠폰 코드 입력 -> invalidAuthCode
        CouponApplyRequest couponApplyRequest = new CouponApplyRequest("invalidAuthCode", 1L, 1L);
        GainedCouponEntity gainedCouponEntity = createGainedCouponEntity(memberEntity, couponEntity);

        given(gainedCouponService.isExistByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(true);
        given(gainedCouponService.findByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(gainedCouponEntity);
        given(placeService.findPlaceById(anyLong())).willReturn(placeEntity);

        //when
        CouponApplyResponse actualResponse = couponApplyUseCase.applyCoupon(1L, couponApplyRequest);

        //then
        assertThat(actualResponse.success()).isFalse();
    }

    @Test
    @DisplayName("사용자는 특정 장소 카테고리의 장소에서 쿠폰을 사용할 수 있다.")
    void successApplyCouponWhenPlaceCategory() {
        MemberEntity memberEntity = createMemberEntity("sub", "text@test.com", SocialPlatform.GOOGLE, "kim");
        CouponEntity couponEntity = createCoupon("name", "url", "description", "couponCode");
        PlaceEntity placeEntity = createPlace(PlaceArea.AREA1, PlaceCategory.CAFFE, "테스트이름", "테스트주소",
                "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL", "테스트쿠폰사용코드");
        CouponApplyRequest couponApplyRequest = new CouponApplyRequest("테스트쿠폰사용코드", 1L, 1L);
        GainedCouponEntity gainedCouponEntity = createGainedCouponEntity(memberEntity, couponEntity);
        RewardList rewardList = new RewardList(false, "couponCode", "emblemCode", false);
        QuestRewardEntity questRewardEntity = createQuestReward(1, rewardList);
        QuestEntity questEntity = createQuest(false, "테스트퀘스트1", PlaceCategory.CAFFE, PlaceArea.NONE, 1);

        given(gainedCouponService.isExistByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(true);
        given(gainedCouponService.findByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(gainedCouponEntity);
        given(couponService.findById(anyLong())).willReturn(couponEntity);
        given(questRewardService.findByCouponCode(anyString())).willReturn(questRewardEntity);
        given(placeService.findByCouponAuthCode(anyString())).willReturn(placeEntity);
        given(questService.findById(anyInt())).willReturn(questEntity);
        given(placeService.findPlaceById(anyLong())).willReturn(placeEntity);

        //when
        CouponApplyResponse actualResponse = couponApplyUseCase.applyCoupon(1L, couponApplyRequest);

        //then
        assertThat(actualResponse.success()).isTrue();
    }


    @Test
    @DisplayName("사용자는 특정 구역에서 쿠폰을 사용할 수 있다.")
    void successApplyCouponWhenPlaceArea() {
        MemberEntity memberEntity = createMemberEntity("sub", "text@test.com", SocialPlatform.GOOGLE, "kim");
        CouponEntity couponEntity = createCoupon("name", "url", "description", "couponCode");
        PlaceEntity placeEntity = createPlace(PlaceArea.AREA1, PlaceCategory.RESTAURANT, "테스트이름", "테스트주소",
                "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL", "테스트쿠폰사용코드");
        CouponApplyRequest couponApplyRequest = new CouponApplyRequest("테스트쿠폰사용코드", 1L, 1L);
        GainedCouponEntity gainedCouponEntity = createGainedCouponEntity(memberEntity, couponEntity);
        RewardList rewardList = new RewardList(false, "couponCode", "emblemCode", false);
        QuestRewardEntity questRewardEntity = createQuestReward(1, rewardList);
        QuestEntity questEntity = createQuest(true, "테스트퀘스트1", PlaceCategory.NONE, PlaceArea.AREA1, 1);

        given(gainedCouponService.isExistByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(true);
        given(gainedCouponService.findByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(gainedCouponEntity);
        given(couponService.findById(anyLong())).willReturn(couponEntity);
        given(questRewardService.findByCouponCode(anyString())).willReturn(questRewardEntity);
        given(placeService.findByCouponAuthCode(anyString())).willReturn(placeEntity);
        given(questService.findById(anyInt())).willReturn(questEntity);
        given(placeService.findPlaceById(anyLong())).willReturn(placeEntity);

        //when
        CouponApplyResponse actualResponse = couponApplyUseCase.applyCoupon(1L, couponApplyRequest);

        //then
        assertThat(actualResponse.success()).isTrue();
    }

    @Test
    @DisplayName("사용자는 특정 장소에서 쿠폰을 사용할 수 있다.")
    void successApplyCouponWithSamePlace() {
        MemberEntity memberEntity = createMemberEntity("sub", "text@test.com", SocialPlatform.GOOGLE, "kim");
        CouponEntity couponEntity = createCoupon("name", "url", "description", "couponCode");
        PlaceEntity placeEntity = createPlace(PlaceArea.NONE, PlaceCategory.NONE, "테스트이름", "테스트주소",
                "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL", "테스트쿠폰사용코드");
        CouponApplyRequest couponApplyRequest = new CouponApplyRequest("테스트쿠폰사용코드", 1L, 1L);
        GainedCouponEntity gainedCouponEntity = createGainedCouponEntity(memberEntity, couponEntity);
        RewardList rewardList = new RewardList(false, "couponCode", "emblemCode", false);
        QuestRewardEntity questRewardEntity = createQuestReward(1, rewardList);
        QuestEntity questEntity = createQuest(true, "테스트퀘스트1", PlaceCategory.NONE, PlaceArea.NONE, 1);

        given(gainedCouponEntity.getSamePlaceRewardPlaceId()).willReturn(placeEntity.getId());

        given(gainedCouponService.isExistByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(true);
        given(gainedCouponService.findByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(gainedCouponEntity);
        given(couponService.findById(anyLong())).willReturn(couponEntity);
        given(questRewardService.findByCouponCode(anyString())).willReturn(questRewardEntity);
        given(placeService.findByCouponAuthCode(anyString())).willReturn(placeEntity);
        given(questService.findById(anyInt())).willReturn(questEntity);
        given(placeService.findPlaceById(anyLong())).willReturn(placeEntity);

        //when
        CouponApplyResponse actualResponse = couponApplyUseCase.applyCoupon(1L, couponApplyRequest);

        //then
        assertThat(actualResponse.success()).isTrue();
    }
}