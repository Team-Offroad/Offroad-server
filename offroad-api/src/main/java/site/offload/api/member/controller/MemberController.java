package site.offload.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.member.dto.request.*;
import site.offload.api.member.dto.response.*;
import site.offload.api.member.usecase.MemberUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController implements MemberControllerSwagger {

    private final MemberUseCase memberUseCase;

    @GetMapping("/adventures/me")
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
        return APISuccessResponse.of(HttpStatus.NO_CONTENT.value(),
                SuccessMessage.MEMBER_PROFILE_UPDATE_SUCCESS.getMessage(), null);
    }

    @GetMapping("/nickname/check")
    public ResponseEntity<APISuccessResponse<NicknameCheckResponse>> checkNickname(@RequestParam(value = "nickname") String nickname) {
        return APISuccessResponse.of(HttpStatus.OK.value(),
                SuccessMessage.CHECK_DUPLICATED_NICKNAME_SUCCESS.getMessage(), NicknameCheckResponse.of(memberUseCase.checkNickname(nickname)));
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

    @PatchMapping("/agree")
    public ResponseEntity<APISuccessResponse<Void>> agreeTerms(
            @RequestBody TermsAgreeRequest termsAgreeRequest
    ) {
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        memberUseCase.updateAgreeTerms(memberId, termsAgreeRequest.marketing());
        return APISuccessResponse.of(HttpStatus.NO_CONTENT.value(), SuccessMessage.CHECK_AGREE_TERMS_SUCCESS.getMessage(), null);
    }
}
