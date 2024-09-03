package site.offload.api.member.dto.response;

public record GainedCharacterResponse(
        Integer characterId,
        String characterName,
        String characterThumbnailImageUrl,
        String characterMainColorCode,
        String characterSubColorCode,
        boolean isNewGained
) {
    public static GainedCharacterResponse of(Integer characterId, String characterName, String characterThumbnailImageUrl, String characterMainColorCode, String characterSubColorCode, boolean isNewGained) {
        return new GainedCharacterResponse(characterId, characterName, characterThumbnailImageUrl, characterMainColorCode, characterSubColorCode, isNewGained);
    }
}
