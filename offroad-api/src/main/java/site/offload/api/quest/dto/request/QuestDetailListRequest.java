package site.offload.api.quest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestDetailListRequest(
        @Schema(description = "퀘스트 진행 여부", example = "true")
        boolean isActive) {
}
