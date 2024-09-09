package site.offload.api.place.controller;

import io.swagger.v3.oas.annotations.Operation;
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
            ResponseEntity<APISuccessResponse<RegisteredPlacesResponse>> getPlaces(
            @RequestParam double currentLatitude,
            @RequestParam double currentLongitude,
            @RequestParam int limit,
            @RequestParam Boolean isBounded
    );
}
