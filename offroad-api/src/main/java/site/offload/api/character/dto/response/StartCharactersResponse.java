package site.offload.api.character.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record StartCharactersResponse(
        @Schema(description = "시작 캐릭터 목록")
        List<StartCharacterResponse> characters
) {
    public static StartCharactersResponse of(List<StartCharacterResponse> characters) {
        return new StartCharactersResponse(characters);
    }
}
