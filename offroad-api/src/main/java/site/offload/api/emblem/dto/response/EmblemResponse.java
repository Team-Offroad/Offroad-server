package site.offload.api.emblem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record EmblemResponse(
        @Schema(description = "칭호 이름")
        String emblemName,
        @Schema(description = "칭호 획득 조건 퀘스트 이름")
        String clearConditionQuestName,
        @Schema(description = "새로 획득한 칭호인지 여부")
        boolean isNewGained
) {
    public static EmblemResponse of(String emblemName, String clearConditionQuestName, boolean isNewGained) {
        return new EmblemResponse(emblemName, clearConditionQuestName, isNewGained);
    }
}
