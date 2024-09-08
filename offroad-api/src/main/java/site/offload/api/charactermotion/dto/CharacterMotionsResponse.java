package site.offload.api.charactermotion.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CharacterMotionsResponse(
        @Schema(description = "획득한 캐릭터 모션 목록")
        List<CharacterMotionResponse> gainedCharacterMotions,
        @Schema(description = "획득하지 못한 캐릭터 모션 목록")
        List<CharacterMotionResponse> notGainedCharacterMotions
) {
    public static CharacterMotionsResponse of(List<CharacterMotionResponse> gainedCharacterMotions, List<CharacterMotionResponse> notGainedCharacterMotions) {
        return new CharacterMotionsResponse(gainedCharacterMotions, notGainedCharacterMotions);
    }
}
