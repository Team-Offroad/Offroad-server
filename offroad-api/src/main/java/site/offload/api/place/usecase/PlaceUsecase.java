package site.offload.api.place.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.api.place.dto.response.RegisteredPlaceResponse;
import site.offload.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.api.place.service.PlaceService;
import sites.offload.db.place.entity.Place;
import sites.offload.external.aws.S3Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceUsecase {

    private final PlaceService placeService;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public RegisteredPlacesResponse checkRegisteredPlaces(Long memberId, RegisteredPlacesRequest registeredPlacesRequest) {

        List<Place> findPlaces = placeService.findPlaces(registeredPlacesRequest);
        List<RegisteredPlaceResponse> registeredPlaces = findPlaces.stream().map(findPlace -> {
            Long count = placeService.countVisitedPlace(memberId, findPlace);
            return RegisteredPlaceResponse.of(
                    findPlace.getId(),
                    findPlace.getName(),
                    findPlace.getAddress(),
                    findPlace.getShortIntroduction(),
                    findPlace.getPlaceCategory(),
                    findPlace.getLatitude(),
                    findPlace.getLongitude(),
                    count,
                    s3Service.getPresignUrl(findPlace.getCategoryImageUrl())
            );
        }).toList();
        return RegisteredPlacesResponse.of(registeredPlaces);
    }
}
