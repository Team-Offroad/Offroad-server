package site.offload.api.character.dto.response;

import lombok.Builder;

@Builder
public record CharacterDetailResponse(
        Integer characterId,
        String characterName,
        String characterBaseImageUrl,
        String characterIconImageUrl,
        String characterDescription,
        String characterSummaryDescription,
        String characterMainColorCode,
        String characterSubColorCode
) {
    public static CharacterDetailResponse of(Integer characterId, String characterName, String characterBaseImageUrl, String characterIconImageUrl, String characterDescription, String characterSummaryDescription, String characterMainColorCode, String characterSubColorCode) {
        return CharacterDetailResponse.builder()
                .characterId(characterId)
                .characterName(characterName)
                .characterBaseImageUrl(characterBaseImageUrl)
                .characterIconImageUrl(characterIconImageUrl)
                .characterDescription(characterDescription)
                .characterSummaryDescription(characterSummaryDescription)
                .characterMainColorCode(characterMainColorCode)
                .characterSubColorCode(characterSubColorCode)
                .build();
    }
}
