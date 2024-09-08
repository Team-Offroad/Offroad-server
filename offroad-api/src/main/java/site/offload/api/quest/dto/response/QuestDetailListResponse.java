package site.offload.api.quest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record QuestDetailListResponse(
        @Schema(description = "퀘스트 목록")
        List<QuestDetailResponse> questList
) {
    public static QuestDetailListResponse of(List<QuestDetailResponse> questList) {
        return new QuestDetailListResponse(questList);
    }
}
