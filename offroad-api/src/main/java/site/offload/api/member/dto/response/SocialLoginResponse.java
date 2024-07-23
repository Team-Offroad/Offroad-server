package site.offload.api.member.dto.response;


import site.offload.api.auth.jwt.TokenResponse;

public record SocialLoginResponse(
        TokenResponse tokens,
        boolean isAlreadyExist
) {
    public static SocialLoginResponse of(TokenResponse tokens, boolean isAlreadyExist) {
        return new SocialLoginResponse(tokens, isAlreadyExist);
    }
}
