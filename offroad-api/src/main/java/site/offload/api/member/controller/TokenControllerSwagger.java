package site.offload.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import site.offload.api.member.dto.response.TokenReissueResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "Token API", description = "토큰 API")
public interface TokenControllerSwagger {

    @Operation(summary = "토큰 재발급 API", description = "Refresh Token으로 Access Token, Refresh Token을 재발급하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {refresh_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "201", description = "토큰 재발급 완료")
    ResponseEntity<APISuccessResponse<TokenReissueResponse>> refreshToken(@RequestHeader("Authorization") String tokenHeaderValue);
}
