package site.offload.api.place.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.exception.NotFoundException;
import site.offload.api.place.dto.request.RegisteredPlacesRequest;
import sites.offload.db.place.entity.PlaceEntity;
import sites.offload.db.place.repository.PlaceRepository;
import sites.offload.db.place.repository.VisitedPlaceRepositoiry;
import sites.offload.enums.ErrorMessage;
import sites.offload.enums.PlaceConstants;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final VisitedPlaceRepositoiry visitedPlaceRepositoiry;

    // TODO: change method name
    public List<PlaceEntity> findPlaces(RegisteredPlacesRequest registeredPlacesRequest) {
        return placeRepository.findAllByCurrentLatitudeAndCurrentLongitude(registeredPlacesRequest.currentLatitude(), registeredPlacesRequest.currentLongitude(), PlaceConstants.RANGE_LATITUDE.getRange(), PlaceConstants.RANGE_LONGITUDE.getRange());
    }

    public Long countVisitedPlace(Long memberId, PlaceEntity findPlaceEntity) {
        return visitedPlaceRepositoiry.countByMemberIdAndPlaceId(memberId, findPlaceEntity.getId());
    }

    public PlaceEntity findPlaceById(final Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PLACE_NOTFOUND_EXCEPTION)
        );
    }
}
