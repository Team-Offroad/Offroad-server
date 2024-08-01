package site.offload.api.member.dto.response;

public record GainedCharacterResponse(
        String characterName,
        String characterThumbnailImageUrl,
        String characterDescription
) {
    public static GainedCharacterResponse of(String characterName, String characterThumbnailImageUrl, String characterDescription) {
        return new GainedCharacterResponse(characterName, characterThumbnailImageUrl, characterDescription);
    }
}
