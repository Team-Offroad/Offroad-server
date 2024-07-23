package site.offload.api.member.dto.response;

public record ChooseCharacterResponse(String characterImageUrl) {
    public static ChooseCharacterResponse of(String characterImageUrl) {
        return new ChooseCharacterResponse(characterImageUrl);
    }
}