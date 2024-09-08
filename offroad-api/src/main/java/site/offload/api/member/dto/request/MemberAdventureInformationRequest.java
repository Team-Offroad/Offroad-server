package site.offload.api.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberAdventureInformationRequest(
        @Schema(description = "회원 ID", example = "1")
        Long memberId,
        @Schema(description = "카테고리", example = "category")
        String category) {

    public static MemberAdventureInformationRequest of(final Long memberId, final String category) {
        return new MemberAdventureInformationRequest(memberId, category);
    }
}
