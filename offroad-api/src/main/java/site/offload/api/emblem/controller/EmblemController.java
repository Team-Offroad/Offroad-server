package site.offload.api.emblem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.emblem.dto.request.UpdateCurrentEmblemRequest;
import site.offload.api.emblem.dto.response.EmblemsResponse;
import site.offload.api.emblem.dto.response.GainedEmblemListResponse;
import site.offload.api.emblem.usecase.EmblemUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmblemController implements EmblemControllerSwagger {

    private final EmblemUseCase emblemUseCase;

    @PatchMapping("/users/emblems")
    public ResponseEntity<APISuccessResponse<Void>> updateEmblem(@RequestParam(value = "emblemCode") final String emblemCode) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        emblemUseCase.updateCurrentEmblem(UpdateCurrentEmblemRequest.of(emblemCode, memberId));
        return APISuccessResponse.of(HttpStatus.NO_CONTENT.value(),
                SuccessMessage.MEMBER_CURRENT_EMBLEM_UPDATE_SUCCESS.getMessage(), null);
    }

    @GetMapping("/users/emblems")
    public ResponseEntity<APISuccessResponse<GainedEmblemListResponse>> getGainedEmblem() {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_GAINED_EMBLEMS_SUCCESS.getMessage(),
                emblemUseCase.getGainedEmblems(memberId));
    }

    @GetMapping("/emblems")
    public ResponseEntity<APISuccessResponse<EmblemsResponse>> getEmblems() {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_EMBLEMS_SUCCESS.getMessage(),
                emblemUseCase.getEmblems(memberId));
    }
}
