package site.offload.api.character.dto.response;

public record CharacterDetailResponse(
        Integer characterId,
        String characterName,
        String characterBaseImageUrl,
        String characterIconImageUrl,
        String characterDescription,
        String characterSummaryDescription,
        String characterMainColorCode,
        String CharacterSubColorCode
) {
    public static CharacterDetailResponse of(Integer characterId, String characterName, String characterBaseImageUrl, String characterIconImageUrl, String characterDescription, String characterSummaryDescription, String characterMainColorCode, String characterSubColorCode) {
        return new CharacterDetailResponse(characterId, characterName, characterBaseImageUrl, characterIconImageUrl, characterDescription, characterSummaryDescription, characterMainColorCode, characterSubColorCode);
    }
}
