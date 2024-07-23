package site.offload.api.character.dto.response;


import java.util.List;

public record CharacterListResponse(List<CharacterResponse> characters) {
    public static CharacterListResponse of(List<CharacterResponse> characters) {
        return new CharacterListResponse(characters);
    }
}
