package site.offload.offloadserver.api.character.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.offloadserver.api.character.dto.response.CharacterListResponse;
import site.offload.offloadserver.api.character.usecase.CharacterUseCase;
import site.offload.offloadserver.api.message.SuccessMessage;
import site.offload.offloadserver.api.response.APISuccessResponse;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController implements CharacterControllerSwagger{

    private final CharacterUseCase characterUseCase;

    @GetMapping
    public ResponseEntity<APISuccessResponse<CharacterListResponse>> getCharacters() {
        return APISuccessResponse.of(HttpStatus.OK.value(),
         SuccessMessage.GET_CHARACTERS_LIST_SUCCESS.getMessage(), characterUseCase.getCharacters());
    }
}
