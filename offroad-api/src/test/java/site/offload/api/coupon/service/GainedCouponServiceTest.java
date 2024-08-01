package site.offload.api.coupon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.db.BaseTimeEntity;
import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;
import site.offload.db.coupon.repository.GainedCouponRepository;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class GainedCouponServiceTest {

    @InjectMocks
    private GainedCouponService gainedCouponService;

    @Mock
    private GainedCouponRepository gainedCouponRepository;

    @Test
    @DisplayName("멤버 id로 생성일 기준 내림차순으로 GainedCoupon을 조회할 수 있다.")
    void findAllByMemberEntityIdOrderByCreatedAtDesc() throws Exception {
        // given
        MemberEntity memberEntity = createMemberEntity("example sub", "example@offroad.com", SocialPlatform.GOOGLE, "김환준");

        CouponEntity couponEntity1 = createCouponEntity("test1", "test1", "test1", "test1");
        CouponEntity couponEntity2 = createCouponEntity("test2", "test2", "test2", "test2");

        GainedCouponEntity coupon1 = createGainedCouponEntity(memberEntity, couponEntity1);
        setGainedCouponEntityCreatedAt(coupon1, LocalDateTime.now().minusDays(1));

        GainedCouponEntity coupon2 = createGainedCouponEntity(memberEntity, couponEntity2);
        setGainedCouponEntityCreatedAt(coupon2, LocalDateTime.now());

        List<GainedCouponEntity> expectCouponList = Arrays.asList(coupon2, coupon1);

        BDDMockito.given(gainedCouponRepository.findAllByMemberEntityIdOrderByCreatedAtDesc(anyLong()))
                .willReturn(expectCouponList);

        // when
        List<GainedCouponEntity> findCouponList = gainedCouponService.findAllByMemberEntityIdOrderByCreatedAtDesc(1L);

        // then
        assertEquals(expectCouponList, findCouponList);
    }

    private void setGainedCouponEntityCreatedAt(GainedCouponEntity entity, LocalDateTime createdAt) throws Exception {
        var field = BaseTimeEntity.class.getDeclaredField("createdAt");
        field.setAccessible(true);
        field.set(entity, createdAt);
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