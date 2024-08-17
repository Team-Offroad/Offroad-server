package site.offload.api.charactermotion.dto;

public record CharacterMotionResponse(
        String category,
        String characterMotionImageUrl,
        boolean isNewGained
) {
    public static CharacterMotionResponse of(String category, String characterMotionImageUrl, boolean isNewGained) {
        return new CharacterMotionResponse(category, characterMotionImageUrl, isNewGained);
    }
}
