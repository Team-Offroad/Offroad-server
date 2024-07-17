package site.offload.offloadserver.api.member.dto.response;

import site.offload.offloadserver.common.jwt.TokenResponse;

public record SocialLoginResponse(
        TokenResponse tokens,
        boolean isAlreadyExist
) {
    public static SocialLoginResponse of(TokenResponse tokens, boolean isAlreadyExist) {
        return new SocialLoginResponse(tokens, isAlreadyExist);
    }
}
