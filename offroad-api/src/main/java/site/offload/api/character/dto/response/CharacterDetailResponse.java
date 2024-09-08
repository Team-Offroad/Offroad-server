package site.offload.api.character.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CharacterDetailResponse(
        @Schema(description = "캐릭터 ID", example = "1")
        Integer characterId,
        @Schema(description = "캐릭터 이름", example = "캐릭터 이름")
        String characterName,
        @Schema(description = "캐릭터 베이스 이미지 URL", example = "https://character-base-image-url.com")
        String characterBaseImageUrl,
        @Schema(description = "캐릭터 아이콘 이미지 URL", example = "https://character-icon-image-url.com")
        String characterIconImageUrl,
        @Schema(description = "캐릭터 설명", example = "캐릭터 설명")
        String characterDescription,
        @Schema(description = "캐릭터 요약 설명", example = "캐릭터 요약 설명")
        String characterSummaryDescription,
        @Schema(description = "캐릭터 메인 색상 코드", example = "#FFFFFF")
        String characterMainColorCode,
        @Schema(description = "캐릭터 서브 색상 코드", example = "#FFFFFF")
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
