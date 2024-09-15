package site.offload.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.api.member.dto.request.*;
import site.offload.api.member.dto.response.*;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "Member API", description = "사용자 관련 API")
public interface MemberControllerSwagger {

    @Operation(summary = "모험 정보 API", description = "메인 홈에서 사용자의 모험 정보(닉네임, 캐릭터 이미지 or 모션 이미지, 칭호)를 조회하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "모험 인증 정보 요청 성공")
    ResponseEntity<APISuccessResponse<MemberAdventureInformationResponse>> getAdventureInformation(
            @Parameter(name = "category", description = "장소 카테고리", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "String")) final String category);

    @Operation(summary = "사용자 프로필 업데이트 API", description = "사용자의 프로필 정보를 받아 업데이트하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "204", description = "프로필 업데이트 성공")
    ResponseEntity<APISuccessResponse<Void>> updateMemberProfile(@RequestBody MemberProfileUpdateRequest memberProfileUpdateRequest);


    @Operation(summary = "닉네임 중복 확인 API", description = "중복된 닉네임이 있는지 확인하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "닉네임 중복 확인 성공")
    ResponseEntity<APISuccessResponse<NicknameCheckResponse>> checkNickname(
            @Parameter(name = "nickname", description = "닉네임", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "String"))
            String nickname);

    @Operation(summary = "캐릭터 선택 API", description = "캐릭터를 선택하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "201", description = "캐릭터 선택 성공")
    ResponseEntity<APISuccessResponse<ChooseCharacterResponse>> chooseCharacter(
            @Parameter(name = "characterId", description = "캐릭터 아이디", in = ParameterIn.PATH, required = true, schema = @Schema(type = "Integer"))
            Integer characterId);

    @Operation(summary = "탐험 QR 인증 API", description = "QR코드로 탐험을 인증하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "탐험 인증 성공")
    ResponseEntity<APISuccessResponse<VerifyQrcodeResponse>> authAdventure(final @RequestBody AuthAdventureRequest request);


    @Operation(summary = "탐험 위치 인증 API", description = "위치 대조로 탐험을 인증하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "탐험 인증 성공")
    ResponseEntity<APISuccessResponse<VerifyPositionDistanceResponse>> authAdventureOnlyPlace(final @RequestBody AuthPositionRequest request);

    @Operation(summary = "로그아웃 API", description = "로그아웃 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "204", description = "로그아웃 성공")
    ResponseEntity<APISuccessResponse<Void>> signOut(@RequestBody final SignOutRequest request);

    @Operation(summary = "전체 캐릭터 목록 조회 API", description = "전체 캐릭터 목록을 조회하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "캐릭터 목록 조회 성공")
    ResponseEntity<APISuccessResponse<GainedCharactersResponse>> getGainedCharacters();

    @Operation(summary = "마이페이지 API", description = "마이페이지를 조회하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공")
    public ResponseEntity<APISuccessResponse<UserInfoResponse>> getUserInfo();

    @Operation(summary = "회원탈퇴 API", description = "회원탈퇴 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "204", description = "마이페이지 조회 성공")
    public ResponseEntity<APISuccessResponse<Void>> softDeleteMember(@RequestBody MemberDeleteRequest request);

    @Operation(summary = "약관 동의 여부 업데이트 API", description = "약관 동의 여부에 따라 업데이트하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "204", description = "약관 동의 여부 업데이트 성공")
    ResponseEntity<APISuccessResponse<Void>> agreeTerms(
            @RequestBody TermsAgreeRequest termsAgreeRequest
    );
}




