package site.offload.api.quest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestInformationResponse(
        @Schema(description = "퀘스트 이름", example = "테스트 퀘스트")
        String questName,
        @Schema(description = "퀘스트 진행도", example = "1")
        int progress,
        @Schema(description = "퀘스트 완료 조건", example = "3")
        int completeCondition
) {
    public static QuestInformationResponse of(String questName, int progress, int completeCondition) {
        return new QuestInformationResponse(questName, progress, completeCondition);
    }
}
