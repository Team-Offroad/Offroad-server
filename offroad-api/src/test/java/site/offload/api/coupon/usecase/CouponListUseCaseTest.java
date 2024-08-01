package site.offload.api.coupon.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.coupon.dto.AvailableCouponResponse;
import site.offload.api.coupon.dto.CouponListResponse;
import site.offload.api.coupon.dto.UsedCouponResponse;
import site.offload.api.coupon.service.GainedCouponService;
import site.offload.db.BaseTimeEntity;
import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CouponListUseCaseTest {

    @InjectMocks
    private CouponListUseCase couponListUseCase;

    @Mock
    private GainedCouponService gainedCouponService;

    @Test
    @DisplayName("쿠폰 목록을 조회하여 사용 가능 및 사용된 쿠폰으로 구분할 수 있다.")
    void getCouponList() throws Exception {
        // given
        MemberEntity memberEntity = createMemberEntity("example sub", "example@offroad.com", SocialPlatform.GOOGLE, "김환준");

        CouponEntity couponEntity1 = createCouponEntity("code1", "description1", "name1", "imageUrl1");
        CouponEntity couponEntity2 = createCouponEntity("code2", "description2", "name2", "imageUrl2");
        CouponEntity couponEntity3 = createCouponEntity("code3", "description3", "name3", "imageUrl3");
        CouponEntity couponEntity4 = createCouponEntity("code4", "description4", "name4", "imageUrl4");

        setGainedCouponEntityId(couponEntity1, 1L);
        setGainedCouponEntityId(couponEntity2, 2L);
        setGainedCouponEntityId(couponEntity3, 3L);
        setGainedCouponEntityId(couponEntity4, 4L);

        GainedCouponEntity gainedCouponEntity1 = createGainedCouponEntity(memberEntity, couponEntity1);
        setGainedCouponEntityCreatedAt(gainedCouponEntity1, LocalDateTime.now().minusDays(3));
        gainedCouponEntity1.updateIsUsed(true);

        GainedCouponEntity gainedCouponEntity2 = createGainedCouponEntity(memberEntity, couponEntity2);
        setGainedCouponEntityCreatedAt(gainedCouponEntity2, LocalDateTime.now().minusDays(2));
        gainedCouponEntity2.updateIsUsed(true);


        GainedCouponEntity gainedCouponEntity3 = createGainedCouponEntity(memberEntity, couponEntity3);
        setGainedCouponEntityCreatedAt(gainedCouponEntity3, LocalDateTime.now().minusDays(1));
        gainedCouponEntity3.updateIsUsed(false);

        GainedCouponEntity gainedCouponEntity4 = createGainedCouponEntity(memberEntity, couponEntity4);
        setGainedCouponEntityCreatedAt(gainedCouponEntity4, LocalDateTime.now());
        gainedCouponEntity4.updateIsUsed(false);


        List<GainedCouponEntity> gainedCouponList = Arrays.asList(gainedCouponEntity4, gainedCouponEntity3, gainedCouponEntity2, gainedCouponEntity1);

        given(gainedCouponService.findAllByMemberEntityIdOrderByCreatedAtDesc(anyLong()))
                .willReturn(gainedCouponList);

        // when
        CouponListResponse response = couponListUseCase.getCouponList(1L);

        // then
        List<AvailableCouponResponse> expectedAvailableCoupons = List.of(
                AvailableCouponResponse.of(
                        couponEntity4.getId(),
                        couponEntity4.getName(),
                        couponEntity4.getCouponImageUrl(),
                        couponEntity4.getDescription()
                ),
                AvailableCouponResponse.of(
                        couponEntity3.getId(),
                        couponEntity3.getName(),
                        couponEntity3.getCouponImageUrl(),
                        couponEntity3.getDescription()
                )
        );

        List<UsedCouponResponse> expectedUsedCoupons = List.of(
                UsedCouponResponse.of(
                        couponEntity2.getName(),
                        couponEntity2.getCouponImageUrl()
                ),
                UsedCouponResponse.of(
                        couponEntity1.getName(),
                        couponEntity1.getCouponImageUrl()
                )
        );

        assertEquals(expectedAvailableCoupons, response.availableCoupons());
        assertEquals(expectedUsedCoupons, response.usedCoupons());
    }

    private void setGainedCouponEntityCreatedAt(GainedCouponEntity entity, LocalDateTime createdAt) throws Exception {
        var field = BaseTimeEntity.class.getDeclaredField("createdAt");
        field.setAccessible(true);
        field.set(entity, createdAt);
    }

    private void setGainedCouponEntityId(CouponEntity entity, Long id) throws Exception {
        var field = CouponEntity.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(entity, id);
    }


    private MemberEntity createMemberEntity(String sub, String email, SocialPlatform socialPlatform, String name) {
        return MemberEntity.builder()
                .sub(sub)
                .email(email)
                .socialPlatform(socialPlatform)
                .name(name)
                .build();
    }

    private CouponEntity createCouponEntity(String couponCode, String description, String name, String couponImageUrl) {
        return CouponEntity.builder()
                .couponCode(couponCode)
                .couponImageUrl(couponImageUrl)
                .description(description)
                .name(name)
                .build();
    }

    private GainedCouponEntity createGainedCouponEntity(MemberEntity memberEntity, CouponEntity couponEntity) {
        return GainedCouponEntity.builder()
                .memberEntity(memberEntity)
                .couponEntity(couponEntity)
                .build();
    }
}