package site.offload.api.emblem.dto.response;

public record EmblemResponse(
        String emblemName,
        String clearConditionQuestName
) {
    public static EmblemResponse of(String emblemName, String clearConditionQuestName) {
        return new EmblemResponse(emblemName, clearConditionQuestName);
    }
}
