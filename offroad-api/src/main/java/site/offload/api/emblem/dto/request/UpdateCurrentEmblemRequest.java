package site.offload.api.emblem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateCurrentEmblemRequest(
        @Schema(description = "엠블럼 코드", example = "emblemCode")
        String emblemCode,
        @Schema(description = "회원 ID", example = "1")
        Long memberId
) {

    public static UpdateCurrentEmblemRequest of(String emblemCode, Long memberId) {
        return new UpdateCurrentEmblemRequest(emblemCode, memberId);
    }
}
