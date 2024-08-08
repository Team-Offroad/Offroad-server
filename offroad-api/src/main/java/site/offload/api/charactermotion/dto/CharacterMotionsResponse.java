package site.offload.api.charactermotion.dto;

import java.util.List;

public record CharacterMotionsResponse(
        List<CharacterMotionResponse> gainedCharacterMotions,
        List<CharacterMotionResponse> notGainedCharacterMotions
) {
    public static CharacterMotionsResponse of(List<CharacterMotionResponse> gainedCharacterMotions, List<CharacterMotionResponse> notGainedCharacterMotions) {
        return new CharacterMotionsResponse(gainedCharacterMotions, notGainedCharacterMotions);
    }
}
