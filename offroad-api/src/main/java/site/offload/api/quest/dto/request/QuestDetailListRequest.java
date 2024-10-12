package site.offload.api.quest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuestDetailListRequest(
        @Schema(description = "페이지네이션 사이즈", example = "1")
        int size,
        @Schema(description = "퀘스트 진행 여부", example = "true")
        boolean isOngoing,
        @Schema(description = "서버의 마지막 응답 객체 id", example = "1")
        int cursor) {

        public static QuestDetailListRequest of(int size, boolean isOngoing, int cursor) {
            return new QuestDetailListRequest(size, isOngoing, cursor);
        }
}
