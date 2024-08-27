package site.offload.api.place.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.exception.NotFoundException;
import site.offload.db.place.entity.PlaceEntity;
import site.offload.db.place.repository.PlaceRepository;
import site.offload.enums.response.ErrorMessage;
import site.offload.enums.place.PlaceConstants;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    // TODO: change method name
    public List<PlaceEntity> findTopNByCurrentLatitudeAndCurrentLongitude(
            double currentLatitude,
            double currentLongitude,
            int limit
    ) {
        return placeRepository.findNearestPlacesByLatitudeAndLongitude(currentLatitude, currentLongitude, limit);
    }

    public List<PlaceEntity> findAllByCurrentLatitudeAndCurrentLongitude(
            double currentLatitude,
            double currentLongitude
    ) {
        return placeRepository.findAllByCurrentLatitudeAndCurrentLongitude(
                currentLatitude,
                currentLongitude,
                PlaceConstants.RANGE_LATITUDE.getRange(),
                PlaceConstants.RANGE_LONGITUDE.getRange()
        );
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
