package site.offload.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
            @ApiResponse(responseCode = "400", description = "모험 인증 정보 요청 실패, 사용자가 획득한 캐릭터가 아닙니다.", content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                    examples = {
                            @ExampleObject(name = "사용자가 획득하지 못한 캐릭터", value = "{ \"message\": \"사용자가 획득한 캐릭터가 아닙니다\", \"customErrorCode\": \"NOT_GAINED_CHARACTER\" }"),
                    })),
            @ApiResponse(responseCode = "404", description = "요청에 필요한 자원이 없음", content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                    examples = {
                            @ExampleObject(name = "존재하지 않는 유저", value = "{ \"message\": \"존재하지 않는 유저\", \"customErrorCode\": \"NOT_EXISTS_MEMBER\" }"),
                            @ExampleObject(name = "존재하지 않는 캐릭터", value = "{ \"message\": \"존재하지 않는 캐릭터\", \"customErrorCode\": \"NOT_EXISTS_CHARACTER\" }"),
                            @ExampleObject(name = "존재하지 않는 장소 카테고리", value = "{ \"message\": \"존재하지 않는 장소 카테고리\", \"customErrorCode\": \"NOT_EXISTS_PLACE_CATEGORY\" }"),
                            @ExampleObject(name = "존재하지 않는 캐릭터 모션", value = "{ \"message\": \"존재하지 않는 캐릭터 모션\", \"customErrorCode\": \"NOT_EXISTS_CHARACTER_MOTION\" }"),
                    }))
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @Parameter(name = "category", description = "장소 카테고리", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<MemberAdventureInformationResponse>> getAdventureInformation(@RequestParam(value = "category") final String category);

    @Operation(summary = "프로필 업데이트 API", description = "프로필 업데이트 정보를 받아 멤버 업데이트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "프로필 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "프로필 업데이트 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<Void>> updateMemberProfile(@RequestBody MemberProfileUpdateRequest memberProfileUpdateRequest);


    @Operation(summary = "닉네임 중복 확인 API", description = "중복된 닉네임이 있는지 확인하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 확인 성공"),
            @ApiResponse(responseCode = "400", description = "닉네임 중복 확인 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @Parameter(name = "nickname", description = "닉네임", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<NicknameCheckResponse>> checkNickname(@RequestParam(value = "nickname") String nickname);

    @Operation(summary = "약관 동의 여부 API", description = "약관 동의 여부 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "약관 동의 여부 반영 성공"),
            @ApiResponse(responseCode = "400", description = "약관 동의 여부 반영 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<Void>> agreeTerms(
            @RequestBody TermsAgreeRequest termsAgreeRequest
    );

    @Operation(summary = "사용자 정보 조회 API", description = "사용자 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 정보 조회 성공"),
            @ApiResponse(responseCode = "400", description = "사용자 정보 조회 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<UserInfoResponse>> getUserInfo();

    @Operation(summary = "사용자 탈퇴 API", description = "사용자 탈퇴 API (소프트 딜리트)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "사용자 탈퇴 성공"),
            @ApiResponse(responseCode = "400", description = "사용자 탈퇴 실패, 올바르지 않은 탈퇴 코드입니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "올바르지 않은 코드",
                                    value = "{ \"message\": \"올바르지 않은 탈퇴 코드입니다.\", \"customErrorCode\": \"INVALID_MEMBER_DELETE_CODE\" }"
                            )
                    ))
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<Void>> softDeleteMember(@RequestBody MemberDeleteRequest request);
}




