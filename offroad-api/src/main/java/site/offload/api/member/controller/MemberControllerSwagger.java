package site.offload.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.api.member.dto.request.*;
import site.offload.api.member.dto.response.*;
import site.offload.api.response.APIErrorResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "[Member API] 사용자 관련 API")
public interface MemberControllerSwagger {

    @Operation(summary = "모험 정보 인증 API", description = "메인 홈에서 모험 인증 정보(닉네임, 캐릭터 이미지 or 모션 이미지, 칭호)반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모험 인증 정보 요청 성공"),
            @ApiResponse(responseCode = "400", description = "모험 인증 정보 요청 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<MemberAdventureInformationResponse>> getAdventureInformation(@RequestParam(value = "category") final String category);

    @Operation(summary = "프로필 업데이트 API", description = "프로필 업데이트 정보를 받아 멤버 업데이트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "프로필 업데이트 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<Void>> updateMemberProfile(@RequestBody MemberProfileUpdateRequest memberProfileUpdateRequest);


    @Operation(summary = "닉네임 중복 확인 API", description = "중복된 닉네임이 있는지 확인하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 확인 성공"),
            @ApiResponse(responseCode = "400", description = "닉네임 중복 확인 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<NicknameCheckResponse>> checkNickname(@RequestParam(value = "nickname") String nickname);

    @Operation(summary = "캐릭터 선택 API", description = "캐릭터를 선택하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐릭터 선택 성공"),
            @ApiResponse(responseCode = "400", description = "캐릭터 선택 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<ChooseCharacterResponse>> chooseCharacter(@PathVariable(value = "characterId") Integer characterId);

    @Operation(summary = "탐험 인증 API", description = "QR코드로 탐험인증 하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "탐험 인증 성공"),
            @ApiResponse(responseCode = "400", description = "탐험 인증 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<VerifyQrcodeResponse>> authAdventure(final @RequestBody AuthAdventureRequest request);


    @Operation(summary = "위치 정보 탐험 인증 API", description = "QR코드로 탐험인증 하는 API")
    @ApiResponse(responseCode = "200", description = "탐험 인증 성공")
    ResponseEntity<APISuccessResponse<VerifyPositionDistanceResponse>> authAdventureOnlyPlace(final @RequestBody AuthPositionRequest request);

    @Operation(summary = "로그아웃 API", description = "로그아웃 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "로그아웃 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<Void>> signOut(@RequestBody final SignOutRequest request);

    @Operation(summary = "캐릭터 목록 조회 API", description = "캐릭터 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐릭터 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "캐릭터 목록 조회 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<GainedCharactersResponse>> getGainedCharacters();

    @Operation(summary = "마케팅 수신 여부 API", description = "마케팅 수신 여부 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마케팅 수신 여부 조회 성공"),
            @ApiResponse(responseCode = "400", description = "마케팅 수신 여부 조회 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<Void>> agreeTerms(
            @RequestBody TermsAgreeRequest termsAgreeRequest
    );

    @Operation(summary = "사용자 정보 조회 API", description = "사용자 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "사용자 정보 조회 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<UserInfoResponse>> getUserInfo();

    @Operation(summary = "사용자 탈퇴 API", description = "사용자 탈퇴 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "사용자 탈퇴 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<Void>> softDeleteMember(@RequestBody MemberDeleteRequest request);
}




