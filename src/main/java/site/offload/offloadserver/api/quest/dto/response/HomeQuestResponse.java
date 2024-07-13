package site.offload.offloadserver.api.quest.dto.response;

import site.offload.offloadserver.db.quest.entity.Quest;

public record HomeQuestResponse(
        QuestInformationResponse recent,
        QuestInformationResponse almost
) {
    public static HomeQuestResponse of(QuestInformationResponse recent, QuestInformationResponse almost) {
        return new HomeQuestResponse(recent, almost);
    }
}
