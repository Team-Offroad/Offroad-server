package site.offload.api.member.usecase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.member.service.MemberService;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;

import static org.mockito.ArgumentMatchers.any;
import static site.offload.api.fixture.MemberEntityFixtureCreator.createMemberEntity;

@ExtendWith(MockitoExtension.class)
public class MemberUseCaseTest {

    @InjectMocks
    MemberUseCase memberUseCase;

    @Mock
    MemberService memberService;

    @Test
    @DisplayName("마케팅 수신 여부를 적용할 수 있다.")
    void applyMarketingAgree() {
        //given
        MemberEntity memberEntity = createMemberEntity("example sub", "example@offroad.com", SocialPlatform.GOOGLE, "이름");
        BDDMockito.given(memberService.findById(any())).willReturn(memberEntity);
        //when
        memberUseCase.updateAgreeMarketing(1L, false);
        //then
        Assertions.assertThat(memberEntity.isAgreeMarketing()).isEqualTo(false);
    }
}
