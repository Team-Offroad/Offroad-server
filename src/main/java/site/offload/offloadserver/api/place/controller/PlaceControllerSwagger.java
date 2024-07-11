package site.offload.offloadserver.api.place.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import site.offload.offloadserver.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.offloadserver.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.offloadserver.api.response.APISuccessResponse;

public interface PlaceControllerSwagger {
    @Operation(summary = "CheckRegisteredPlaces API", description = "오프로드 등록 장소 조회 API입니다.")
    @ApiResponse(responseCode = "200",
            description = "오프로드 등록 장소 조회 완료",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    public ResponseEntity<APISuccessResponse<RegisteredPlacesResponse>> checkRegisteredPlaces(RegisteredPlacesRequest registeredPlacesRequest);
}
