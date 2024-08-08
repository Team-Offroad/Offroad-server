package site.offload.api.place.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.exception.NotFoundException;
import site.offload.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.db.place.entity.PlaceEntity;
import site.offload.db.place.repository.PlaceRepository;
import site.offload.db.place.repository.VisitedPlaceRepository;
import site.offload.enums.response.ErrorMessage;
import site.offload.enums.place.PlaceConstants;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final VisitedPlaceRepository visitedPlaceRepository;

    // TODO: change method name
    public List<PlaceEntity> findPlaces(RegisteredPlacesRequest registeredPlacesRequest) {
        return placeRepository.findAllByCurrentLatitudeAndCurrentLongitude(registeredPlacesRequest.currentLatitude(), registeredPlacesRequest.currentLongitude(), PlaceConstants.RANGE_LATITUDE.getRange(), PlaceConstants.RANGE_LONGITUDE.getRange());
    }

    public Long countVisitedPlace(Long memberId, PlaceEntity findPlaceEntity) {
        return visitedPlaceRepository.countByMemberEntityIdAndPlaceEntityId(memberId, findPlaceEntity.getId());
    }

    public PlaceEntity findPlaceById(final Long placeId) {
        return placeRepository.findById(placeId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PLACE_NOTFOUND_EXCEPTION)
        );
    }

    public PlaceEntity findByCouponAuthCode(final String couponAuthCode) {
        return placeRepository.findByCouponAuthCode(couponAuthCode).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PLACE_NOTFOUND_EXCEPTION)
        );
    }
}
