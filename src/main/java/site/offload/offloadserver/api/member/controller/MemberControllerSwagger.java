package site.offload.offloadserver.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.offloadserver.api.member.dto.request.AuthAdventureRequest;
import site.offload.offloadserver.api.member.dto.request.AuthPositionRequest;
import site.offload.offloadserver.api.member.dto.request.MemberProfileUpdateRequest;
import site.offload.offloadserver.api.member.dto.response.*;
import site.offload.offloadserver.api.response.APISuccessResponse;

public interface MemberControllerSwagger {

    @Operation(summary = "모험 정보 인증 API", description = "메인 홈에서 모험 인증 정보(닉네임, 캐릭터 이미지 or 모션 이미지, 칭호)반환")
    @ApiResponse(responseCode = "200",
            description = "모험 인증 정보 요청 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    ResponseEntity<APISuccessResponse<MemberAdventureInformationResponse>> getAdventureInformation(@RequestParam(value = "category") final String category
    );

    @Operation(summary = "프로필 업데이트 API", description = "프로필 업데이트 정보를 받아 멤버 업데이트")
    @ApiResponse(responseCode = "200",
            description = "프로필 업데이트 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    public ResponseEntity<APISuccessResponse<Void>> updateMemberProfile(@RequestBody MemberProfileUpdateRequest memberProfileUpdateRequest);


    @Operation(summary = "닉네임 중복 확인 API", description = "중복된 닉네임이 있는지 확인하는 API")
    @ApiResponse(responseCode = "200",
            description = "닉네임 중복 확인 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    public ResponseEntity<APISuccessResponse<NicknameCheckResponse>> checkNickname(@RequestParam(value = "nickname") String nickname);

    @Operation(summary = "캐릭터 선택 API", description = "캐릭터를 선택하는 API")
    @ApiResponse(responseCode = "200",
            description = "캐릭터 선택 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    public ResponseEntity<APISuccessResponse<ChooseCharacterResponse>> chooseCharacter(@PathVariable(value = "characterId") Integer characterId);

    @Operation(summary = "탐험 인증 API", description = "QR코드로 탐험인증 하는 API")
    @ApiResponse(responseCode = "200",
            description = "탐험 인증 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    ResponseEntity<APISuccessResponse<VerifyQrcodeResponse>> authAdventure(final @RequestBody AuthAdventureRequest request);



    @Operation(summary = "위치 정보 탐험 인증 API", description = "QR코드로 탐험인증 하는 API")
    @ApiResponse(responseCode = "200",
            description = "탐험 인증 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    ResponseEntity<APISuccessResponse<VerifyPositionDistanceResponse>> authAdventureOnlyPlace(final @RequestBody AuthPositionRequest request);
}




