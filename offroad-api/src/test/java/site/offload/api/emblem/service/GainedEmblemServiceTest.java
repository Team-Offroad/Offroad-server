package site.offload.api.emblem.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.db.emblem.entity.GainedEmblemEntity;
import site.offload.db.emblem.repository.GainedEmblemRepository;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;

import static org.mockito.ArgumentMatchers.any;
import static site.offload.api.fixture.GainedEmblemEntityFixtureCreator.createGainedEmblemEntity;

@ExtendWith(MockitoExtension.class)
public class GainedEmblemServiceTest {

    @InjectMocks
    GainedEmblemService gainedEmblemService;

    @Mock
    GainedEmblemRepository gainedEmblemRepository;

    @Test
    @DisplayName("멤버 아이디와 칭호 코드로 해당 칭호를 얻었는지 확인할 수 있다.")
    void checkByMemberIdAndEmblemCode() {
        //given
        MemberEntity memberEntity = MemberEntity.builder().name("이름1").email("이메일1").sub("소셜아이디1").socialPlatform(SocialPlatform.GOOGLE).build();
        GainedEmblemEntity gainedEmblemEntity = createGainedEmblemEntity(memberEntity, "emblemCode");

        BDDMockito.given(gainedEmblemRepository.findByMemberEntityIdAndEmblemCode(any(), any())).willReturn(gainedEmblemEntity);

        //when

        GainedEmblemEntity expected = gainedEmblemService.findByMemberIdAndEmblemCode(1L, "emblemCode");

        //then

        Assertions.assertThat(expected).isEqualTo(gainedEmblemEntity);
    }
}
