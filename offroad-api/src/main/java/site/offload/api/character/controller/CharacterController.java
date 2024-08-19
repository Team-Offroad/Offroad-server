package site.offload.api.character.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.character.dto.response.CharacterDetailResponse;
import site.offload.api.character.dto.response.StartCharactersResponse;
import site.offload.api.character.usecase.CharacterUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController implements CharacterControllerSwagger {

    private final CharacterUseCase characterUseCase;

    @GetMapping
    public ResponseEntity<APISuccessResponse<StartCharactersResponse>> getStartCharacters() {
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.GET_START_CHARACTERS_SUCCESS.getMessage(), characterUseCase.getCharacters());
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<APISuccessResponse<CharacterDetailResponse>> getCharacterDetail(
            @PathVariable(value = "characterId") Integer characterId
    ) {
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.GET_CHARACTER_DETAIL_SUCCESS.getMessage(), characterUseCase.getCharacterDetail(characterId));
    }
}
