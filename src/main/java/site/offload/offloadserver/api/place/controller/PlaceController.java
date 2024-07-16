package site.offload.offloadserver.api.place.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.offload.offloadserver.api.message.SuccessMessage;
import site.offload.offloadserver.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.offloadserver.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.offloadserver.api.place.usecase.PlaceUsecase;
import site.offload.offloadserver.api.response.APISuccessResponse;
import site.offload.offloadserver.common.auth.PrincipalHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Place API", description = "장소 관련 API")
public class PlaceController implements PlaceControllerSwagger {

    private final PlaceUsecase placeUsecase;

    @GetMapping("/places")
    public ResponseEntity<APISuccessResponse<RegisteredPlacesResponse>> checkRegisteredPlaces(@RequestParam double currentLatitude, @RequestParam double currentLongitude) {

        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.CHECK_REGISTERED_PLACES_SUCCESS.getMessage(), placeUsecase.checkRegisteredPlaces(PrincipalHandler.getMemberIdFromPrincipal(), RegisteredPlacesRequest.of(currentLatitude, currentLongitude)));
    }
}
