package site.offload.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record NicknameCheckResponse(
        @Schema(description = "닉네임 중복 여부", example = "true")
        boolean isDuplicate
) {
    public static NicknameCheckResponse of(boolean isDuplicate) {
        return new NicknameCheckResponse(isDuplicate);
    }
}
