package site.offload.api.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import site.offload.api.auth.jwt.JwtTokenProvider;
import site.offload.api.auth.jwt.TokenResponse;
import site.offload.api.exception.UnAuthorizedException;
import site.offload.api.member.dto.response.TokenReissueResponse;
import sites.offload.enums.ErrorMessage;


@Service
@RequiredArgsConstructor
public class TokenUseCase {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenReissueResponse reissueTokens(final Long memberId, final String refreshToken) {
        if (isRefreshTokenValidate(memberId, refreshToken)) {
            TokenResponse tokenResponse = jwtTokenProvider.reissueToken(memberId);
            return TokenReissueResponse.of(tokenResponse.accessToken(), tokenResponse.refreshToken());
        } else {
            throw new UnAuthorizedException(ErrorMessage.JWT_REISSUE_EXCEPTION);
        }
    }

    private boolean isRefreshTokenValidate(final Long memberId, final String refreshToken) {
        final String findMemberId = redisTemplate.opsForValue().get(refreshToken);
        return findMemberId.equals(String.valueOf(memberId));
    }


}
