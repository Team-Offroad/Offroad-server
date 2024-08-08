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
import org.assertj.core.api.Assertions;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GainedCouponServiceTest {

    @InjectMocks
    private GainedCouponService gainedCouponService;

    @Mock
    private GainedCouponRepository gainedCouponRepository;

    @Test
    @DisplayName("사용자는 획득한 쿠폰을 최신순으로 조회할 수 있다.")
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

        given(gainedCouponRepository.findAllByMemberEntityIdOrderByCreatedAtDesc(anyLong()))
                .willReturn(expectCouponList);

        // when
        List<GainedCouponEntity> findCouponList = gainedCouponService.findAllByMemberEntityIdOrderByCreatedAtDesc(1L);

        // then
        assertEquals(expectCouponList, findCouponList);
    }

    @Test
    @DisplayName("멤버 id와 쿠폰 id로 획득한 쿠폰의 존재여부를 확인할 수 있다.")
    void isExistByMemberEntityIdAndCouponId() {
        //given
        given(gainedCouponService.isExistByMemberEntityIdAndCouponId(anyLong(), anyLong())).willReturn(true);

        //when
        boolean expectedExistBoolean = gainedCouponService.isExistByMemberEntityIdAndCouponId(1L, 1L);

        //then
        Assertions.assertThat(expectedExistBoolean).isTrue();
    }


    @Test
    @DisplayName("획득한 쿠폰을 저장할 수 있다.")
    void save() {
        // given
        MemberEntity memberEntity = createMemberEntity("example sub", "example@offroad.com", SocialPlatform.GOOGLE, "김환준");
        CouponEntity couponEntity = createCouponEntity("testCoupon", "testDescription", "testName", "testImageUrl");
        GainedCouponEntity gainedCouponEntity = createGainedCouponEntity(memberEntity, couponEntity);

        // when
        gainedCouponService.save(gainedCouponEntity);

        // then
        verify(gainedCouponRepository).save(gainedCouponEntity);
    }

    @Test
    @DisplayName("멤버 id와 쿠폰 id로 획득한 쿠폰을 조회할 수 있다")
    void findByMemberEntityIdAndCouponId() {
        //given
        MemberEntity memberEntity = createMemberEntity("example sub", "example@offroad.com", SocialPlatform.GOOGLE, "김환준");
        CouponEntity couponEntity = createCouponEntity("test1", "test1", "test1", "test1");
        GainedCouponEntity gainedCouponEntity = createGainedCouponEntity(memberEntity, couponEntity);
        given(gainedCouponRepository.findByMemberEntityIdAndCouponEntityId(anyLong(), anyLong())).willReturn(Optional.of(gainedCouponEntity));

        //when
        GainedCouponEntity expectedGainedCouponEntity = gainedCouponService.findByMemberEntityIdAndCouponId(1L, 1L);

        //then
        Assertions.assertThat(expectedGainedCouponEntity).isEqualTo(gainedCouponEntity);
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