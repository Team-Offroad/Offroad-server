package site.offload.offloadserver.api.place.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.offloadserver.api.place.dto.response.RegisteredPlaceResponse;
import site.offload.offloadserver.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.offloadserver.api.place.service.PlaceService;
import site.offload.offloadserver.db.place.entity.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PlaceUsecase {

    private final PlaceService placeService;

    @Transactional(readOnly = true)
    public RegisteredPlacesResponse checkRegisteredPlaces(Long memberId, RegisteredPlacesRequest registeredPlacesRequest) {

        List<Place> findPlaces = placeService.findPlaces(registeredPlacesRequest);
        List<Long> visitCounts = findPlaces.stream()
                .map(place -> placeService.countVisitedPlace(memberId, place))
                .toList();
        List<RegisteredPlaceResponse> registeredPlaces = IntStream.range(0, findPlaces.size())
                .mapToObj(i -> {
                    Place place = findPlaces.get(i);
                    Long count = visitCounts.get(i);
                    return RegisteredPlaceResponse.of(
                            place.getId(),
                            place.getName(),
                            place.getAddress(),
                            place.getShortIntroduction(),
                            place.getPlaceCategory(),
                            place.getLatitude(),
                            place.getLongitude(),
                            count);
                }).toList();

        return RegisteredPlacesResponse.of(registeredPlaces);
    }
}
