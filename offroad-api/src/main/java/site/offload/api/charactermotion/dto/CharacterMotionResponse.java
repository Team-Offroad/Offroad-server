package site.offload.api.charactermotion.dto;

public record CharacterMotionResponse(
        String category,
        String characterMotionImageUrl
) {
    public static CharacterMotionResponse of(String category, String characterMotionImageUrl) {
        return new CharacterMotionResponse(category, characterMotionImageUrl);
    }
}
