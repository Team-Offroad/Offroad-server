package site.offload.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import site.offload.api.member.dto.request.LogoutRequest;
import site.offload.api.member.dto.response.SocialLoginResponse;
import site.offload.api.response.APIErrorResponse;
import site.offload.api.response.APISuccessResponse;
import site.offload.external.oauth.dto.SocialLoginRequest;

@Tag(name = "[SignIn API] 로그인 관련 API")
public interface SocialLoginControllerSwagger {

    @Operation(summary = "소셜 로그인 API", description = "구글 및 애플 소셜 로그인 구현")
    @ApiResponse(responseCode = "200", description = "소셜 로그인 성공")
    ResponseEntity<APISuccessResponse<SocialLoginResponse>> login(
            @RequestBody SocialLoginRequest socialLoginRequest
    );

    @Operation(summary = "로그아웃 API", description = "로그아웃 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "로그아웃 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    ResponseEntity<APISuccessResponse<Void>> logout(@RequestBody final LogoutRequest request);
}
