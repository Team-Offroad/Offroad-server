package site.offload.api.emblem.dto.response;

public record EmblemResponse(
        String emblemName,
        String clearConditionQuestName,
        boolean isNewGained
) {
    public static EmblemResponse of(String emblemName, String clearConditionQuestName, boolean isNewGained) {
        return new EmblemResponse(emblemName, clearConditionQuestName, isNewGained);
    }
}
