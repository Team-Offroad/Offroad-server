package site.offload.api.charactermotion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.charactermotion.dto.CharacterMotionsResponse;
import site.offload.api.charactermotion.usecase.CharacterMotionUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequestMapping("/api/motions")
@RequiredArgsConstructor
public class CharacterMotionController implements CharacterMotionControllerSwagger {

    private final CharacterMotionUseCase characterMotionUseCase;

    @GetMapping("/{characterId}")
    public ResponseEntity<APISuccessResponse<CharacterMotionsResponse>> getMotions(
            @PathVariable(value = "characterId") Integer characterId
    ) {
        Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_MOTIONS_SUCCESS.getMessage(), characterMotionUseCase.getMotions(memberId, characterId));
    }
}
