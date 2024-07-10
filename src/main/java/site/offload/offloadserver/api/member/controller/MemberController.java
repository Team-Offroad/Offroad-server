package site.offload.offloadserver.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.offloadserver.api.member.dto.MemberAdventureInformationRequest;
import site.offload.offloadserver.api.member.dto.MemberAdventureInformationResponse;
import site.offload.offloadserver.api.member.usecase.MemberUseCase;
import site.offload.offloadserver.api.message.SuccessMessage;
import site.offload.offloadserver.api.response.APISuccessResponse;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController implements MemberControllerSwagger {

    private final MemberUseCase memberUseCase;
    private final PrincipalHandler principalHandler;

    @GetMapping("/adventures/informations")
    public ResponseEntity<APISuccessResponse<MemberAdventureInformationResponse>> getAdventureInformation(@RequestParam(value = "category") final String category,
                                                                                                          @RequestParam(value = "characterId") final Integer characterId) {
        final Long memberId = principalHandler.getUserIdFromPrincipal;
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.MEMBER_ADVENTURE_INFORMATION_SUCCESS.toString(),
                memberUseCase.getMemberAdventureInformation(MemberAdventureInformationRequest.of(memberId, category, characterId)));
    }
}
