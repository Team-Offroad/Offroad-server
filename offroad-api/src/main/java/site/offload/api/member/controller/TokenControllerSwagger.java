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
import org.springframework.web.bind.annotation.RequestHeader;
import site.offload.api.member.dto.response.TokenReissueResponse;
import site.offload.api.response.APIErrorResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "[Token API] 토큰 관련 API")
public interface TokenControllerSwagger {

    @Operation(summary = "토큰 재발급 API", description = "Refresh Token으로 Access Token, Refresh Token을 재발급하는 API입니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "토큰 재발급 완료"),
                    @ApiResponse(responseCode = "401", description = "토큰 재발급 권한 없음", content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "유효하지 않은 리프레시 토큰", value = "{ \"message\": \"유효하지 않은 리프레시 토큰\", \"customErrorCode\": \"FAIL_REISSUE_TOKEN\" }"),
                            })),
            }
    )
    @Parameter(name = "Authorization", description = "Bearer {refresh_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<TokenReissueResponse>> refreshToken(@RequestHeader("Authorization") String tokenHeaderValue);
}
