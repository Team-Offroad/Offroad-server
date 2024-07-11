package site.offload.offloadserver.api.emblem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.offloadserver.api.emblem.dto.request.UpdateCurrentEmblemRequest;
import site.offload.offloadserver.api.emblem.dto.response.GainedEmblemListResponse;
import site.offload.offloadserver.api.emblem.usecase.EmblemUseCase;
import site.offload.offloadserver.api.message.SuccessMessage;
import site.offload.offloadserver.api.response.APISuccessResponse;
import site.offload.offloadserver.common.auth.PrincipalHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/emblems")
public class EmblemController implements EmblemControllSwawgger{

    private final PrincipalHandler principalHandler;
    private final EmblemUseCase emblemUseCase;

    @PatchMapping
    public ResponseEntity<APISuccessResponse<Void>> updateEmblem(@RequestParam(value = "emblemCode") final String emblemCode) {
        final Long memberId = principalHandler.getMemberIdFromPrincipal();
        emblemUseCase.updateCurrentEmblem(UpdateCurrentEmblemRequest.of(emblemCode, memberId));
        return APISuccessResponse.of(HttpStatus.OK.value(),
         SuccessMessage.MEMBER_CURRENT_EMBLEM_UPDATE_SUCCESS.getMessage(), null);
    }

    @GetMapping
    public ResponseEntity<APISuccessResponse<GainedEmblemListResponse>> getGainedEmblem() {
        final Long memberId = principalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_GAINED_EMBLEM_SUCCESS.getMessage(),
                                     emblemUseCase.getGainedEmblems(memberId));
    }
}
