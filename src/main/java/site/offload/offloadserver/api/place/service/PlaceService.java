package site.offload.offloadserver.api.place.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.api.place.dto.constans.PlaceConstants;
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

    // TODO: change method name
    public List<Place> findPlaces(RegisteredPlacesRequest registeredPlacesRequest) {
        return placeRepository.findAllByCurrentLatitudeAndCurrentLongitude(registeredPlacesRequest.currentLatitude(), registeredPlacesRequest.currentLongitude(), PlaceConstants.RANGE_LATITUDE.getRange(), PlaceConstants.RANGE_LONGITUDE.getRange());
    }

    public Long countVisitedPlace(Long memberId, Place findPlace) {
        return visitedPlaceRepositoiry.countByMemberIdAndPlaceId(memberId, findPlace.getId());
    }

    public Place findPlaceById(final Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PLACE_NOTFOUND_EXCEPTION)
        );
    }
}
