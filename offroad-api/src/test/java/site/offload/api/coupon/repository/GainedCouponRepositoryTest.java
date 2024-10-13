package site.offload.api.coupon.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import site.offload.api.fixture.CouponEntityFixtureCreator;
import site.offload.api.fixture.GainedCouponEntityFixtureCreator;
import site.offload.api.fixture.MemberEntityFixtureCreator;
import site.offload.db.config.JpaAuditingConfig;
import site.offload.db.coupon.entity.CouponEntity;
import site.offload.db.coupon.entity.GainedCouponEntity;
import site.offload.db.coupon.repository.CouponRepository;
import site.offload.db.coupon.repository.GainedCouponRepository;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.member.repository.MemberRepository;
import site.offload.enums.member.SocialPlatform;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(JpaAuditingConfig.class)
class GainedCouponRepositoryTest {

    @Autowired
    private GainedCouponRepository gainedCouponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰 목록 페이지네이션과  내림차순 정렬이 잘 동작한다.")
    void testPaginationAndSortingOfGainedCoupons() {
        // Given
        MemberEntity member = MemberEntityFixtureCreator.createMemberEntity("sub", "email", SocialPlatform.GOOGLE, "name");
        CouponEntity coupon = CouponEntityFixtureCreator.createCoupon("name", "image", "description", "code");

        memberRepository.save(member);
        couponRepository.save(coupon);

        GainedCouponEntity gainedCoupon1 = GainedCouponEntityFixtureCreator.createGainedCouponEntity(member, coupon, 1L);
        GainedCouponEntity gainedCoupon2 = GainedCouponEntityFixtureCreator.createGainedCouponEntity(member, coupon, 2L);
        GainedCouponEntity gainedCoupon3 = GainedCouponEntityFixtureCreator.createGainedCouponEntity(member, coupon, 3L);
        GainedCouponEntity gainedCoupon4 = GainedCouponEntityFixtureCreator.createGainedCouponEntity(member, coupon, 4L);
        GainedCouponEntity gainedCoupon5 = GainedCouponEntityFixtureCreator.createGainedCouponEntity(member, coupon, 5L);

        gainedCouponRepository.save(gainedCoupon1);
        gainedCouponRepository.save(gainedCoupon2);
        gainedCouponRepository.save(gainedCoupon3);
        gainedCouponRepository.save(gainedCoupon4);
        gainedCouponRepository.save(gainedCoupon5);

        gainedCoupon1.updateIsUsed(true);
        gainedCoupon2.updateIsUsed(true);
        gainedCoupon3.updateIsUsed(true);
        gainedCoupon4.updateIsUsed(true);
        gainedCoupon5.updateIsUsed(true);

        // When
        Pageable pageable = PageRequest.of(0, 2);
        List<GainedCouponEntity> firstUsedPage = gainedCouponRepository.findByMemberEntityIdAndIdLessThanAndIsUsedOrderByIdDesc(
                member.getId(), Long.MAX_VALUE, true, pageable);

        // Then
        assertEquals(2, firstUsedPage.size());
        assertEquals(5L, firstUsedPage.get(0).getAcquisitionPlaceId());
        assertEquals(4L, firstUsedPage.get(1).getAcquisitionPlaceId());

        // When
        GainedCouponEntity unusedCoupon1 = GainedCouponEntityFixtureCreator.createGainedCouponEntity(member, coupon, 6L);
        GainedCouponEntity unusedCoupon2 = GainedCouponEntityFixtureCreator.createGainedCouponEntity(member, coupon, 7L);
        gainedCouponRepository.save(unusedCoupon1);
        gainedCouponRepository.save(unusedCoupon2);

        // When
        List<GainedCouponEntity> firstUnusedPage = gainedCouponRepository.findByMemberEntityIdAndIdLessThanAndIsUsedOrderByIdDesc(
                member.getId(), Long.MAX_VALUE, false, pageable);

        // Then
        assertEquals(2, firstUnusedPage.size());
        assertEquals(7L, firstUnusedPage.get(0).getAcquisitionPlaceId());
        assertEquals(6L, firstUnusedPage.get(1).getAcquisitionPlaceId());
    }
}
