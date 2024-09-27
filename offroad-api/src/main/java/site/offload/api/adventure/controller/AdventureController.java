package site.offload.api.adventure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.adventure.dto.request.AuthAdventureRequest;
import site.offload.api.adventure.dto.request.AuthPositionRequest;
import site.offload.api.adventure.dto.response.VerifyPositionDistanceResponse;
import site.offload.api.adventure.dto.response.VerifyQrcodeResponse;
import site.offload.api.adventure.usecase.AuthAdventureUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adventure")
public class AdventureController implements AdventureControllerSwagger {

    private final AuthAdventureUseCase authAdventureUseCase;

    @PostMapping("/authentication/QR")
    public ResponseEntity<APISuccessResponse<VerifyQrcodeResponse>> authAdventure(final @RequestBody AuthAdventureRequest request) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.AUTHENTICATE_ADVENTURE_REQUEST_SUCCESS.getMessage(), authAdventureUseCase.authAdventure(memberId, request));
    }

    @PostMapping("/authentication/distance")
    public ResponseEntity<APISuccessResponse<VerifyPositionDistanceResponse>> authAdventureOnlyPlace(final @RequestBody AuthPositionRequest request) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.AUTHENTICATE_ADVENTURE_REQUEST_SUCCESS.getMessage(), authAdventureUseCase.authAdventurePosition(memberId, request));
    }
}
