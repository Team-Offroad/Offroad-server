package site.offload.offloadserver.api.place.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.place.entity.Place;
import site.offload.offloadserver.db.place.entity.VisitedPlace;
import site.offload.offloadserver.db.place.repository.PlaceRepository;
import site.offload.offloadserver.db.place.repository.VisitedPlaceRepositoiry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final VisitedPlaceRepositoiry visitedPlaceRepositoiry;

    public List<Place> findPlaces(RegisteredPlacesRequest registeredPlacesRequest) {
        return placeRepository.findByCurrentLatitudeAndCurrentLongitude(registeredPlacesRequest.currentLatitude(), registeredPlacesRequest.currentLongitude());
    }

    public List<Integer> countVisitedPlace(Long memberId, List<Place> findPlaces) {
        List<Integer> countVisit = findPlaces.stream()
                .map(place -> visitedPlaceRepositoiry.countByMemberIdAndPlaceId(memberId, place.getId()))
                .collect(Collectors.toList());

        return countVisit;
    }
}
