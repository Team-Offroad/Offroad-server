package site.offload.offloadserver.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.offloadserver.api.member.dto.request.AuthAdventureRequest;
import site.offload.offloadserver.api.member.dto.request.MemberProfileUpdateRequest;
import site.offload.offloadserver.api.member.dto.request.MemberAdventureInformationRequest;
import site.offload.offloadserver.api.member.dto.response.AuthAdventureResponse;
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

    @GetMapping("/adventures/informations")
    public ResponseEntity<APISuccessResponse<MemberAdventureInformationResponse>> getAdventureInformation(@RequestParam(value = "category") final String category
    ) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.MEMBER_ADVENTURE_INFORMATION_SUCCESS.getMessage(),
                memberUseCase.getMemberAdventureInformation(MemberAdventureInformationRequest.of(memberId, category)));
    }

    @PatchMapping("/profiles")
    public ResponseEntity<APISuccessResponse<Void>> updateMemberProfile(@RequestBody MemberProfileUpdateRequest memberProfileUpdateRequest) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        memberUseCase.updateMemberProfile(memberId, memberProfileUpdateRequest);
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.MEMBER_PROFILE_UPDATE_SUCCESS.getMessage(), null);
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<APISuccessResponse<Boolean>> checkNickname(@RequestParam(value = "nickname") String nickname) {
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.CHECK_DUPLICATED_NICKNAME_SUCCESS.getMessage(), memberUseCase.checkNickname(nickname));
    }

    @PostMapping("/characters/{characterId}")
    public ResponseEntity<APISuccessResponse<Void>> chooseCharacter(@PathVariable(value = "characterId") Integer characterId) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        memberUseCase.chooseCharacter(memberId, characterId);
        return APISuccessResponse.of(HttpStatus.CREATED.value(),
                SuccessMessage.CHOOSE_CHARACTER_SUCCESS.getMessage(), null);
    }

    @PostMapping("/adventures/authentication")
    public ResponseEntity<APISuccessResponse<AuthAdventureResponse>> authAdventure(final @RequestBody AuthAdventureRequest request) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.AUTHENTICATE_ADVENTURE_REQUEST_SUCCESS.getMessage(), memberUseCase.authAdventure(memberId, request));
    }
}
