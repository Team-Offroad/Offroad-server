package site.offload.api.place.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.api.place.dto.response.RegisteredPlaceResponse;
import site.offload.api.place.dto.response.RegisteredPlacesResponse;
import site.offload.api.place.service.PlaceService;
import site.offload.api.place.service.VisitedPlaceService;
import site.offload.db.place.entity.PlaceEntity;
import site.offload.external.aws.S3Service;

@Service
@RequiredArgsConstructor
public class PlaceUseCase {

    private final PlaceService placeService;
    private final S3Service s3Service;
    private final VisitedPlaceService visitedPlaceService;

    @Transactional(readOnly = true)
    public RegisteredPlacesResponse checkRegisteredPlaces(Long memberId, RegisteredPlacesRequest request) {
        if (request.isVisit()) {
            return
                    RegisteredPlacesResponse.of(
                            visitedPlaceService.findAllByMemberId(memberId)
                                    .stream().map(
                                            visitedPlace -> {
                                                final Long count = placeService.countVisitedPlace(memberId, visitedPlace.getPlaceEntity());
                                                final PlaceEntity placeEntity = visitedPlace.getPlaceEntity();
                                                return RegisteredPlaceResponse.of(
                                                        placeEntity.getId(),
                                                        placeEntity.getName(),
                                                        placeEntity.getAddress(),
                                                        placeEntity.getShortIntroduction(),
                                                        placeEntity.getPlaceCategory(),
                                                        placeEntity.getLatitude(),
                                                        placeEntity.getLongitude(),
                                                        count,
                                                        s3Service.getPresignUrl(visitedPlace.getPlaceEntity().getCategoryImageUrl())
                                                );
                                            }).toList()
                    );
        } else {
            return RegisteredPlacesResponse.of(
                    placeService.findPlaces(request).stream().map(
                            findPlace -> {
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
                            }
                    ).toList()
            );
        }
    }
}
