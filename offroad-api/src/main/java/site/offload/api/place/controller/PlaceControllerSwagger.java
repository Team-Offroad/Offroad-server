package site.offload.api.place.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

@Tag(name = "Place API", description = "장소 관련 API")

public interface PlaceControllerSwagger {
    @Operation(summary = "오프로드 등록 장소 조회 API", description = "오프로드에 등록된 장소를 조회하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "오프로드 등록 장소 조회 완료")
    ResponseEntity<APISuccessResponse<RegisteredPlacesResponse>> getPlaces(
            @Parameter(name = "currentLatitude", description = "현재 위도", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "double"))
            double currentLatitude,
            @Parameter(name = "currentLongitude", description = "현재 경도", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "double"))
            double currentLongitude,
            @Parameter(name = "limit", description = "제한 거리", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "int"))
            int limit,
            @Parameter(name = "isBounded", description = "범위 내 여부", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "Boolean"))
            Boolean isBounded
    );
}
