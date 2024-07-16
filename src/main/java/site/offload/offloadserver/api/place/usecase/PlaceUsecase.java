package site.offload.offloadserver.api.place.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.offloadserver.api.place.dto.response.RegisteredPlaceResponse;
import site.offload.offloadserver.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.offloadserver.api.place.service.PlaceService;
import site.offload.offloadserver.db.place.entity.Place;
import site.offload.offloadserver.external.aws.S3UseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PlaceUsecase {

    private final PlaceService placeService;
    private final S3UseCase s3UseCase;

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
                    s3UseCase.getPresignUrl(findPlace.getCategoryImageUrl())
            );
        }).toList();
        return RegisteredPlacesResponse.of(registeredPlaces);
    }
}
