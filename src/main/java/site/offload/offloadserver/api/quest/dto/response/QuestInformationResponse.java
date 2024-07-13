package site.offload.offloadserver.api.quest.dto.response;

public record QuestInformationResponse(
        String questName,
        int progress,
        int completeCondition
) {
    public static QuestInformationResponse of(String questName, int progress, int completeCondition) {
        return new QuestInformationResponse(questName, progress, completeCondition);
    }
}
