package site.offload.offloadserver.api.quest.dto.response;

public record QuestResponse(
        QuestInformationResponse recent,
        QuestInformationResponse almost
) {
    public static QuestResponse of(QuestInformationResponse recent, QuestInformationResponse almost) {
        return new QuestResponse(recent, almost);
    }
}
