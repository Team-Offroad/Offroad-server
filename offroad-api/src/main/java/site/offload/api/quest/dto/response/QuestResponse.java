package site.offload.api.quest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
public record QuestResponse(
        @Schema(description = "최근 퀘스트 정보")
        QuestInformationResponse recent,
        @Schema(description = "거의 완료된 퀘스트 정보")
        QuestInformationResponse almost
) {
    public static QuestResponse of(QuestInformationResponse recent, QuestInformationResponse almost) {
        return new QuestResponse(recent, almost);
    }
}
