package site.offload.api.place.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.place.dto.request.UnvisitedPlacesRequest;
import site.offload.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.api.place.dto.response.UnvisitedPlacesResponse;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@Tag(name = "[Place API] 오프로드 등록 장소 관련 API")
public interface PlaceControllerSwagger {
    @Operation(summary = "오프로드 등록 장소 조회 API", description = "오프로드 등록 장소 조회 API입니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "오프로드 등록 장소 조회 완료"),
                    @ApiResponse(responseCode = "400", description = "오프로드 등록 장소 조회 실패", content = @Content(schema = @Schema(implementation = SuccessMessage.class)))
            }
    )
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @Parameter(name = "currentLatitude", description = "현재 위도", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "double"))
    @Parameter(name = "currentLongitude", description = "현재 경도", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "double"))
    @Parameter(name = "limit", description = "조회할 장소 개수", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "int"))
    @Parameter(name = "isBounded", description = "true일 경우 현재 위치 기준 반경 내 장소, false일 경우 전체 장소", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "Boolean"))
    ResponseEntity<APISuccessResponse<RegisteredPlacesResponse>> getPlaces(
            @RequestParam double currentLatitude,
            @RequestParam double currentLongitude,
            @RequestParam int limit,
            @RequestParam Boolean isBounded
    );
}
