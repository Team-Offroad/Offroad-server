package site.offload.offloadserver.api.place.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.offloadserver.api.place.dto.response.RegisteredPlaceResponse;
import site.offload.offloadserver.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.offloadserver.api.place.service.PlaceService;
import site.offload.offloadserver.db.place.entity.Place;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceUsecase {

    private final PlaceService placeService;

    public RegisteredPlacesResponse checkRegisteredPlaces(Long memberId, RegisteredPlacesRequest registeredPlacesRequest) {

        List<Place> findPlaces = placeService.findPlaces(registeredPlacesRequest);
        List<Integer> visitCounts = placeService.countVisitedPlace(memberId, findPlaces);
        List<RegisteredPlaceResponse> registeredPlaces = new ArrayList<RegisteredPlaceResponse>();

        for (int i = 0; i < findPlaces.size(); i++) {
            Place place = findPlaces.get(i);
            Integer count = visitCounts.get(i);
            registeredPlaces.add(RegisteredPlaceResponse.of(place.getId(), place.getName(), place.getAddress(), place.getShortIntroduction(), place.getPlaceCategory(), place.getLatitude(), place.getLongitude(), count));
        }

        return RegisteredPlacesResponse.of(registeredPlaces);
    }
}
