package site.offload.api.character.dto.response;


import java.util.List;

public record StartCharactersResponse(List<StartCharacterResponse> characters) {
    public static StartCharactersResponse of(List<StartCharacterResponse> characters) {
        return new StartCharactersResponse(characters);
    }
}
