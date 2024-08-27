package site.offload.api.place.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

public interface PlaceControllerSwagger {
    @Operation(summary = "오프로드 등록 장소 조회 API", description = "오프로드 등록 장소 조회 API입니다.")
    @ApiResponse(responseCode = "200",
            description = "오프로드 등록 장소 조회 완료",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
            ResponseEntity<APISuccessResponse<RegisteredPlacesResponse>> getPlaces(
            @RequestParam double currentLatitude,
            @RequestParam double currentLongitude,
            @RequestParam int limit,
            @RequestParam Boolean isBounded
    );
}
