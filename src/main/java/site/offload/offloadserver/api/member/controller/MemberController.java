package site.offload.offloadserver.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.offloadserver.api.member.dto.request.MemberProfileUpdateRequest;
import site.offload.offloadserver.api.member.dto.request.MemberAdventureInformationRequest;
import site.offload.offloadserver.api.member.dto.response.MemberAdventureInformationResponse;
import site.offload.offloadserver.api.member.usecase.MemberUseCase;
import site.offload.offloadserver.api.message.SuccessMessage;
import site.offload.offloadserver.api.response.APISuccessResponse;
import site.offload.offloadserver.common.auth.PrincipalHandler;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController implements MemberControllerSwagger {

    private final MemberUseCase memberUseCase;
    private final PrincipalHandler principalHandler;

    @GetMapping("/adventures/informations")
    public ResponseEntity<APISuccessResponse<MemberAdventureInformationResponse>> getAdventureInformation(@RequestParam(value = "category") final String category,
                                                                                                          @RequestParam(value = "characterId") final Integer characterId) {
        final Long memberId = principalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.MEMBER_ADVENTURE_INFORMATION_SUCCESS.getMessage(),
                memberUseCase.getMemberAdventureInformation(MemberAdventureInformationRequest.of(memberId, category, characterId)));
    }

    @PatchMapping("/profiles")
    public ResponseEntity<APISuccessResponse<Object>> updateMemberProfile(@RequestBody MemberProfileUpdateRequest memberProfileUpdateRequest) {
        final Long memberId = principalHandler.getMemberIdFromPrincipal();
        memberUseCase.updateMemberProfile(memberId, memberProfileUpdateRequest);
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.MEMBER_PROFILE_UPDATE_SUCCESS.getMessage(), null);
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<APISuccessResponse<Object>> checkNickname(@RequestParam(value = "nickname") String nickname) {

        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.CHECK_DUPLICATED_NICKNAME.getMessage(), memberUseCase.checkNickname(nickname));
    }
}
