package site.offload.api.place.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.api.place.usecase.PlaceUsecase;
import site.offload.api.response.APISuccessResponse;
import sites.offload.enums.SuccessMessage;

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
