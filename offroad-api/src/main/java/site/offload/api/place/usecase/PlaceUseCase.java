package site.offload.api.place.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.api.place.dto.request.UnvisitedPlacesRequest;
import site.offload.api.place.dto.response.RegisteredPlaceResponse;
import site.offload.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.api.place.dto.response.UnvisitedPlaceResponse;
import site.offload.api.place.dto.response.UnvisitedPlacesResponse;
import site.offload.api.place.service.PlaceService;
import site.offload.api.place.service.VisitedPlaceService;
import site.offload.external.aws.S3Service;


@Service
@RequiredArgsConstructor
public class PlaceUseCase {

    private final PlaceService placeService;
    private final S3Service s3Service;
    private final VisitedPlaceService visitedPlaceService;

    @Transactional(readOnly = true)
    public RegisteredPlacesResponse checkRegisteredPlaces(Long memberId, RegisteredPlacesRequest request) {

        return RegisteredPlacesResponse.of(
                placeService.findPlaces(request.currentLatitude(), request.currentLongitude())
                        .stream()
                        .map(findPlace -> {
                            Long count = visitedPlaceService.countByMemberIdAndPlace(memberId, findPlace);
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
                        }).toList()
        );
    }

    @Transactional(readOnly = true)
    public UnvisitedPlacesResponse checkUnvisitedPlaces(Long memberId, UnvisitedPlacesRequest request) {
            return UnvisitedPlacesResponse.of(
                    placeService.findUnvisitedPlaces(memberId, request.currentLatitude(), request.currentLongitude())
                            .stream()
                            .map(findPlace -> UnvisitedPlaceResponse.of(
                                    findPlace.getId(),
                                    findPlace.getName(),
                                    findPlace.getAddress(),
                                    findPlace.getShortIntroduction(),
                                    findPlace.getPlaceCategory(),
                                    findPlace.getLatitude(),
                                    findPlace.getLongitude(),
                                    0L,
                                    s3Service.getPresignUrl(findPlace.getCategoryImageUrl())
                            )).toList()
            );
    }
}
