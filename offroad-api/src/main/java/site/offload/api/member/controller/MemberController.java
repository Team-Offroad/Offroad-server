package site.offload.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.member.dto.request.*;
import site.offload.api.member.dto.response.*;
import site.offload.api.member.usecase.AuthAdventureUseCase;
import site.offload.api.member.usecase.MemberUseCase;
import site.offload.api.member.usecase.SignOutUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController implements MemberControllerSwagger {

    private final MemberUseCase memberUseCase;
    private final SignOutUseCase signOutUseCase;
    private final AuthAdventureUseCase authAdventureUseCase;

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
    public ResponseEntity<APISuccessResponse<NicknameCheckResponse>> checkNickname(@RequestParam(value = "nickname") String nickname) {
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.CHECK_DUPLICATED_NICKNAME_SUCCESS.getMessage(), NicknameCheckResponse.of(memberUseCase.checkNickname(nickname)));
    }

    @PostMapping("/characters/{characterId}")
    public ResponseEntity<APISuccessResponse<ChooseCharacterResponse>> chooseCharacter(@PathVariable(value = "characterId") Integer characterId) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.CREATED.value(),
                SuccessMessage.CHOOSE_CHARACTER_SUCCESS.getMessage(), memberUseCase.chooseCharacter(memberId, characterId));
    }

    @PostMapping("/adventures/authentication")
    public ResponseEntity<APISuccessResponse<VerifyQrcodeResponse>> authAdventure(final @RequestBody AuthAdventureRequest request) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.AUTHENTICATE_ADVENTURE_REQUEST_SUCCESS.getMessage(), authAdventureUseCase.authAdventure(memberId, request));
    }

    @PostMapping("/places/distance")
    public ResponseEntity<APISuccessResponse<VerifyPositionDistanceResponse>> authAdventureOnlyPlace(final @RequestBody AuthPositionRequest request) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.AUTHENTICATE_ADVENTURE_REQUEST_SUCCESS.getMessage(), authAdventureUseCase.authAdventurePosition(memberId, request));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<APISuccessResponse<Void>> signOut(@RequestBody final SignOutRequest request) {
        signOutUseCase.execute(request);
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.SIGN_OUT_SUCCESS.getMessage(), null);
    }

    @GetMapping("/characters")
    public ResponseEntity<APISuccessResponse<GainedCharactersResponse>> getGainedCharacters() {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_GAINED_CHARACTERS_SUCCESS.getMessage(), memberUseCase.getGainedCharacters(memberId));
    }

    @GetMapping("/me")
    public ResponseEntity<APISuccessResponse<UserInfoResponse>> getUserInfo() {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_USER_INFO_SUCCESS.getMessage(), memberUseCase.getUserInfo(memberId));
    }


    @PostMapping("/delete")
    public ResponseEntity<APISuccessResponse<Void>> softDeleteMember(@RequestBody MemberDeleteRequest request) {
        memberUseCase.softDeleteMemberById(PrincipalHandler.getMemberIdFromPrincipal(), request);
        return APISuccessResponse.of(HttpStatus.ACCEPTED.value(), SuccessMessage.SOFT_DELETE_MEMBER_SUCCESS.getMessage(), null);
    }
  
    @PatchMapping("/marketing")
    public ResponseEntity<APISuccessResponse<Void>> agreeMarketing(
            @RequestBody MarketingAgreeRequest marketingAgreeRequest
    ) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        memberUseCase.updateAgreeMarketing(memberId, marketingAgreeRequest.marketing());
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.CHECK_AGREE_MARKETING_SUCCESS.getMessage(), null);
    }
}
