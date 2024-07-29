package site.offload.api.member.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.auth.jwt.JwtTokenProvider;
import site.offload.api.auth.jwt.TokenResponse;
import site.offload.api.exception.UnAuthorizedException;
import site.offload.api.member.dto.response.TokenReissueResponse;
import site.offload.enums.response.ErrorMessage;
import site.offload.cache.config.RedisRepository;


@Service
@RequiredArgsConstructor
public class TokenUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRepository redisRepository;

    public TokenReissueResponse reissueTokens(final Long memberId, final String refreshToken) {
        if (isRefreshTokenValidate(memberId, refreshToken)) {
            TokenResponse tokenResponse = jwtTokenProvider.reissueToken(memberId);
            return TokenReissueResponse.of(tokenResponse.accessToken(), tokenResponse.refreshToken());
        } else {
            throw new UnAuthorizedException(ErrorMessage.JWT_REISSUE_EXCEPTION);
        }
    }

    private boolean isRefreshTokenValidate(final Long memberId, final String refreshToken) {
        final String findMemberId = redisRepository.find(refreshToken);
        return findMemberId.equals(String.valueOf(memberId));
    }


}
