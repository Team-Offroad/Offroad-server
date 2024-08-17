package site.offload.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import site.offload.api.auth.jwt.JwtTokenProvider;
import site.offload.api.exception.ForbiddenException;
import site.offload.api.member.service.MemberService;
import site.offload.cache.member.service.MemberStatusCacheService;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.MemberStatus;
import site.offload.enums.response.ErrorMessage;

@Component
@RequiredArgsConstructor
public class MemberStatusInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final MemberStatusCacheService memberStatusCacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Authorization 헤더가 없는 경우 또는 Bearer 토큰이 아닌 경우 무시
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return true;
        }

        final String token = authorizationHeader.substring("Bearer ".length());
        final Long memberId = jwtTokenProvider.getUserFromJwt(token);

        // 캐시에서 회원 상태 조회
        String status = memberStatusCacheService.find(memberId);

        // 캐시가 없는 경우 DB에서 조회하고 캐시 업데이트
        if (status == null) {
            final MemberEntity memberEntity = memberService.findById(memberId);
            status = memberEntity.getMemberStatus().name();
            memberStatusCacheService.saveMemberStatus(status, memberId);
        }

        // 상태가 INACTIVE인 경우 예외
        if (MemberStatus.INACTIVE.name().equals(status)) {
            throw new ForbiddenException(ErrorMessage.MEMBER_STATUS_INACTIVE);
        }

        return true;
    }
}
