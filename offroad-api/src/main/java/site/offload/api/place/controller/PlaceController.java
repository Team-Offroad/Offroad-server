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
import site.offload.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.api.place.usecase.PlaceUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/places")
public class PlaceController implements PlaceControllerSwagger {

    private final PlaceUseCase placeUsecase;

    @GetMapping
    public ResponseEntity<APISuccessResponse<RegisteredPlacesResponse>> getPlaces(
            @RequestParam(value = "currentLatitude") double currentLatitude,
            @RequestParam(value = "currentLongitude") double currentLongitude,
            @RequestParam(value = "limit") int limit,
            @RequestParam(value = "isBounded") Boolean isBounded
    ) {
        return APISuccessResponse.of(
                HttpStatus.OK.value(),
                SuccessMessage.CHECK_REGISTERED_PLACES_SUCCESS.getMessage(),
                placeUsecase.getPlaces(PrincipalHandler.getMemberIdFromPrincipal(), currentLatitude, currentLongitude, limit, isBounded));
    }
}
