package site.offload.offloadserver.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import site.offload.offloadserver.api.member.dto.response.TokenReissueResponse;
import site.offload.offloadserver.api.response.APISuccessResponse;

public interface TokenControllerSwagger {

    @Operation(summary = "토큰 재발급 API", description = "Refresh Token으로 Access Token, Refresh Token을 재발급하는 API입니다.")
    @ApiResponse(responseCode = "201",
            description = "토큰 재발급 완료",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    ResponseEntity<APISuccessResponse<TokenReissueResponse>> refreshToken(@RequestHeader("Authorization") String tokenHeaderValue);
}
