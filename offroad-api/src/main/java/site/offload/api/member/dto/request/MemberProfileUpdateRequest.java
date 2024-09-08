package site.offload.api.member.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import site.offload.enums.member.MemberGender;

public record MemberProfileUpdateRequest(
        @Schema(description = "닉네임", example = "닉네임")
        String nickname,
        @Schema(description = "년도", example = "2021")
        Integer year,
        @Schema(description = "월", example = "1")
        Integer month,
        @Schema(description = "일", example = "1")
        Integer day,
        @Schema(description = "성별", example = "MALE")
        MemberGender gender
) {
    public static MemberProfileUpdateRequest of(String nickname, Integer year, Integer month, Integer day, MemberGender gender) {
        return new MemberProfileUpdateRequest(nickname, year, month, day, gender);
    }
}
