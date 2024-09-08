package site.offload.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "획득한 캐릭터 정보")
public record GainedCharacterResponse(
        @Schema(description = "캐릭터 ID", example = "1")
        Integer characterId,
        @Schema(description = "캐릭터 이름", example = "테스트 캐릭터")
        String characterName,
        @Schema(description = "캐릭터 썸네일 이미지 URL", example = "https://test.com/test.jpg")
        String characterThumbnailImageUrl,
        @Schema(description = "캐릭터 메인 색상 코드", example = "FFFFFF")
        String characterMainColorCode,
        @Schema(description = "캐릭터 서브 색상 코드", example = "000000")
        String characterSubColorCode,
        @Schema(description = "신규 획득 여부", example = "true")
        boolean isNewGained
) {
    public static GainedCharacterResponse of(Integer characterId, String characterName, String characterThumbnailImageUrl, String characterMainColorCode, String characterSubColorCode, boolean isNewGained) {
        return new GainedCharacterResponse(characterId, characterName, characterThumbnailImageUrl, characterMainColorCode, characterSubColorCode, isNewGained);
    }
}
