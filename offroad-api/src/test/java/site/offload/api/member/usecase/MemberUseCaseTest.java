package site.offload.api.member.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.exception.BadRequestException;
import site.offload.api.member.dto.request.MemberDeleteRequest;
import site.offload.api.member.service.MemberService;
import site.offload.cache.member.service.MemberStatusCacheService;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.MemberStatus;
import site.offload.api.util.MemberDeleteLoggingUtil;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MemberUseCaseTest {

    @Mock
    private MemberService memberService;

    @Mock
    private MemberStatusCacheService memberStatusCacheService;

    @Mock
    private MemberDeleteLoggingUtil memberDeleteLoggingUtil;

    @InjectMocks
    private MemberUseCase memberUseCase;

    private static final String MEMBER_DELETE_CODE = "오프로드 회원을 탈퇴하겠습니다.";

    @Test
    @DisplayName("사용자는 올바른 회원 탈퇴 문구를 입력하면 회원 탈퇴를 할 수 있다.")
    void deleteWithValidDeleteCode() {
        // given
        Long memberId = 1L;
        MemberDeleteRequest request = new MemberDeleteRequest(MEMBER_DELETE_CODE);

        MemberEntity memberEntity = mock(MemberEntity.class);
        given(memberService.findById(memberId)).willReturn(memberEntity);

        // when
        memberUseCase.softDeleteMemberById(memberId, request);

        // then
        then(memberEntity).should().updateMemberStatus(MemberStatus.INACTIVE);
        then(memberStatusCacheService).should().saveMemberStatus(MemberStatus.INACTIVE.name(), memberId);
    }

    @Test
    @DisplayName("사용자는 올바른 회원 탈퇴 문구를 입력하지 않으면 회원 탈퇴에 실패한다.")
    void deleteWithWrongDeleteCode() {
        // given
        Long memberId = 1L;
        MemberDeleteRequest request = new MemberDeleteRequest("잘못된 코드");

        // when, then
        assertThrows(BadRequestException.class, () ->
                memberUseCase.softDeleteMemberById(memberId, request)
        );

        then(memberService).shouldHaveNoInteractions();
        then(memberStatusCacheService).shouldHaveNoInteractions();
        then(memberDeleteLoggingUtil).shouldHaveNoInteractions();
    }
}
