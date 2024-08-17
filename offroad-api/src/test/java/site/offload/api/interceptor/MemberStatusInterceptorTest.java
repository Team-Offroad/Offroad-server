package site.offload.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import site.offload.api.auth.jwt.JwtTokenProvider;
import site.offload.api.exception.ForbiddenException;
import site.offload.api.member.service.MemberService;
import site.offload.cache.member.service.MemberStatusCacheService;
import site.offload.enums.member.MemberStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

class MemberStatusInterceptorTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private MemberService memberService;

    @Mock
    private MemberStatusCacheService memberStatusCacheService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private MemberStatusInterceptor memberStatusInterceptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Active 상태인 사용자는 인터셉터를 통과한다.")
    void activeMemberTest() throws Exception {
        // given
        given(request.getHeader("Authorization")).willReturn("Bearer valid_token");
        given(jwtTokenProvider.getUserFromJwt("valid_token")).willReturn(1L);
        given(memberStatusCacheService.find(1L)).willReturn(MemberStatus.ACTIVE.name());

        // when
        boolean result = memberStatusInterceptor.preHandle(request, response, new Object());

        // then
        then(memberStatusCacheService).should(never()).saveMemberStatus(anyString(), anyLong());
        assert (result);
    }

    @Test
    @DisplayName("Inactive 상태인 사용자는 인터셉터에서 403 예외가 발생한다.")
    void inactiveMemberTest() throws Exception {
        // given
        given(request.getHeader("Authorization")).willReturn("Bearer valid_token");
        given(jwtTokenProvider.getUserFromJwt("valid_token")).willReturn(1L);
        given(memberStatusCacheService.find(1L)).willReturn(MemberStatus.INACTIVE.name());

        // when, then
        assertThrows(ForbiddenException.class, () ->
                memberStatusInterceptor.preHandle(request, response, new Object())
        );
    }
}
