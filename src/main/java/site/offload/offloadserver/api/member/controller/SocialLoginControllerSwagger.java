package site.offload.offloadserver.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import site.offload.offloadserver.api.member.dto.request.SocialLoginRequest;
import site.offload.offloadserver.api.member.dto.response.SocialLoginResponse;
import site.offload.offloadserver.api.response.APISuccessResponse;
import site.offload.offloadserver.common.jwt.TokenResponse;

public interface SocialLoginControllerSwagger {

    @Operation(summary = "소셜 로그인 API", description = "구글 및 애플 소셜 로그인 구현")
    @ApiResponse(responseCode = "200",
            description = "소셜 로그인 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    public ResponseEntity<APISuccessResponse<SocialLoginResponse>> login(
            @RequestBody SocialLoginRequest socialLoginRequest
    );
}
