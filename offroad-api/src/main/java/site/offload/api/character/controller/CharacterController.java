package site.offload.api.character.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.character.dto.response.CharacterDetailResponse;
import site.offload.api.character.dto.response.StartCharactersResponse;
import site.offload.api.character.usecase.CharacterUseCase;
import site.offload.api.member.dto.response.ChooseCharacterResponse;
import site.offload.api.member.dto.response.GainedCharactersResponse;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController implements CharacterControllerSwagger {

    private final CharacterUseCase characterUseCase;

    @GetMapping("/start")
    public ResponseEntity<APISuccessResponse<StartCharactersResponse>> getStartCharacters() {
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.GET_START_CHARACTERS_SUCCESS.getMessage(), characterUseCase.getStartCharacters());
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<APISuccessResponse<CharacterDetailResponse>> getCharacterDetail(
            @PathVariable(value = "characterId") Integer characterId
    ) {
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.GET_CHARACTER_DETAIL_SUCCESS.getMessage(), characterUseCase.getCharacterDetail(characterId));
    }

    @PostMapping("/{characterId}")
    public ResponseEntity<APISuccessResponse<ChooseCharacterResponse>> chooseCharacter(@PathVariable(value = "characterId") Integer characterId) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.CREATED.value(),
                SuccessMessage.CHOOSE_CHARACTER_SUCCESS.getMessage(), characterUseCase.chooseCharacter(memberId, characterId));
    }

    @GetMapping
    public ResponseEntity<APISuccessResponse<GainedCharactersResponse>> getCharacters() {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_GAINED_CHARACTERS_SUCCESS.getMessage(), characterUseCase.getCharacters(memberId));
    }
}
