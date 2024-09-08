package site.offload.api.quest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CompleteQuestResponse(
        @Schema(description = "퀘스트 이름", example = "테스트 퀘스트")
        String name
) {

    public static CompleteQuestResponse of(String name) {
        return new CompleteQuestResponse(name);
    }
}
