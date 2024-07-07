package site.offload.offloadserver.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import site.offload.offloadserver.api.response.APISuccessResponse;

public interface HealthCheckControllerSwagger {

    @Operation(summary = "HealthCheck API", description = "HealthCheck Test API입니다.")
    // @Operation(summary = "API 요약 설명", description = "API 상세 설명")
    @ApiResponse(responseCode = "200",
            description = "healthCheck test 완료",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    public String healthCheck();
}
