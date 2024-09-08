package site.offload.api.quest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record QuestDetailResponse(
        @Schema(description = "퀘스트 이름", example = "테스트 퀘스트")
        String questName,
        @Schema(description = "퀘스트 설명", example = "테스트 퀘스트 설명")
        String description,
        @Schema(description = "퀘스트 진행도", example = "1")
        int currentCount,
        @Schema(description = "퀘스트 완료 조건", example = "3")
        int totalCount,
        @Schema(description = "퀘스트 완료 조건", example = "3")
        String requirement,
        @Schema(description = "퀘스트 보상", example = "~쿠폰")
        String reward) {

    public static QuestDetailResponse of(String questName, String description,
                                         int currentCount, int totalCount, String requirement,
                                         String reward) {
        return QuestDetailResponse.builder()
                .questName(questName)
                .currentCount(currentCount)
                .totalCount(totalCount)
                .requirement(requirement)
                .reward(reward)
                .description(description)
                .build();
    }
}
