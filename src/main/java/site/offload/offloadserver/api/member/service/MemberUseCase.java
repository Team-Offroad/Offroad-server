package site.offload.offloadserver.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.api.exception.UnAuthorizedException;
import site.offload.offloadserver.api.member.dto.response.TokenReissueResponse;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.common.jwt.JwtTokenProvider;
import site.offload.offloadserver.common.jwt.TokenResponse;

@Service
@RequiredArgsConstructor
public class MemberUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public TokenReissueResponse reissueTokens(final Long memberId, final String refreshToken) {
        if (isRefreshTokenValidate(memberId, refreshToken)) {
            TokenResponse tokenResponse = jwtTokenProvider.reissueToken(memberId);
            return TokenReissueResponse.of(tokenResponse.accessToken(), tokenResponse.refreshToken());
        } else {
            throw new UnAuthorizedException(ErrorMessage.JWT_REISSUE_EXCEPTION);
        }
    }

    private boolean isRefreshTokenValidate(final Long memberId, final String refreshToken) {
        final String findRefreshToken = redisTemplate.opsForValue().get(String.valueOf(memberId));
        return refreshToken.equals(findRefreshToken);
    }
}
