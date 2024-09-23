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

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PlaceUseCase {

    private final PlaceService placeService;
    private final S3Service s3Service;
    private final VisitedPlaceService visitedPlaceService;

    @Transactional(readOnly = true)
    public RegisteredPlacesResponse getPlaces(
            final Long memberId,
            final double currentLatitude,
            final double currentLongitude,
            final int limit,
            Boolean isBounded
    ) {
        // isBounded가 true일 경우, 현재 위치를 기준으로 반경 내에 있는 모든 장소를 조회
        if (Objects.nonNull(isBounded) && isBounded) {
            return RegisteredPlacesResponse.of(
                    placeService.findAllByCurrentLatitudeAndCurrentLongitude(currentLatitude, currentLongitude)
                            .stream()
                            .map(findPlace -> {
                                Long count = visitedPlaceService.countByMemberIdAndPlace(memberId, findPlace);
                                return RegisteredPlaceResponse.of(
                                        findPlace.getId(),
                                        findPlace.getName(),
                                        findPlace.getAddress(),
                                        findPlace.getShortIntroduction(),
                                        findPlace.getPlaceCategory(),
                                        findPlace.getPlaceArea().getPlaceAreaAlias(),
                                        findPlace.getLatitude(),
                                        findPlace.getLongitude(),
                                        count,
                                        s3Service.getPresignUrl(findPlace.getCategoryImageUrl())
                                );
                            }).toList()
            );
        }

        return RegisteredPlacesResponse.of(
                placeService.findTopNByCurrentLatitudeAndCurrentLongitude(currentLatitude, currentLongitude, limit)
                        .stream()
                        .map(findPlace -> {
                            Long count = visitedPlaceService.countByMemberIdAndPlace(memberId, findPlace);
                            return RegisteredPlaceResponse.of(
                                    findPlace.getId(),
                                    findPlace.getName(),
                                    findPlace.getAddress(),
                                    findPlace.getShortIntroduction(),
                                    findPlace.getPlaceCategory(),
                                    findPlace.getPlaceArea().getPlaceAreaAlias(),
                                    findPlace.getLatitude(),
                                    findPlace.getLongitude(),
                                    count,
                                    s3Service.getPresignUrl(findPlace.getCategoryImageUrl())
                            );
                        }).toList()
        );
    }
}
