package site.offload.api.charactermotion.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CharacterMotionResponse(
        @Schema(description = "캐릭터 모션 카테고리", example = "CAFE")
        String category,
        @Schema(description = "캐릭터 모션 이미지 URL", example = "https://offload.site/character-motion/idle.png")
        String characterMotionImageUrl,
        @Schema(description = "신규 획득 여부", example = "true")
        boolean isNewGained
) {
    public static CharacterMotionResponse of(String category, String characterMotionImageUrl, boolean isNewGained) {
        return new CharacterMotionResponse(category, characterMotionImageUrl, isNewGained);
    }
}
