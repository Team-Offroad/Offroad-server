package site.offload.api.emblem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.emblem.dto.request.UpdateCurrentEmblemRequest;
import site.offload.api.emblem.dto.response.GainedEmblemListResponse;
import site.offload.api.emblem.usecase.EmblemUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/emblems")
public class EmblemController implements EmblemControllerSwagger {

    private final EmblemUseCase emblemUseCase;

    @PatchMapping
    public ResponseEntity<APISuccessResponse<Void>> updateEmblem(@RequestParam(value = "emblemCode") final String emblemCode) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        emblemUseCase.updateCurrentEmblem(UpdateCurrentEmblemRequest.of(emblemCode, memberId));
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.MEMBER_CURRENT_EMBLEM_UPDATE_SUCCESS.getMessage(), null);
    }

    @GetMapping
    public ResponseEntity<APISuccessResponse<GainedEmblemListResponse>> getGainedEmblem() {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_GAINED_EMBLEM_SUCCESS.getMessage(),
                emblemUseCase.getGainedEmblems(memberId));
    }
}
