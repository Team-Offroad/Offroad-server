package site.offload.api.member.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import site.offload.api.auth.jwt.TokenResponse;

public record SocialLoginResponse(
        @Schema(description = "토큰 정보")
        TokenResponse tokens,
        @Schema(description = "이미 가입된 회원인지 여부", example = "true")
        boolean isAlreadyExist
) {
    public static SocialLoginResponse of(TokenResponse tokens, boolean isAlreadyExist) {
        return new SocialLoginResponse(tokens, isAlreadyExist);
    }
}
