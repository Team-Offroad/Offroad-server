package site.offload.offloadserver.api.place.service;


import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.offloadserver.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.offloadserver.db.place.entity.Place;
import site.offload.offloadserver.db.place.entity.PlaceArea;
import site.offload.offloadserver.db.place.entity.PlaceCategory;
import site.offload.offloadserver.db.place.repository.PlaceRepository;
import site.offload.offloadserver.db.place.repository.VisitedPlaceRepositoiry;


import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @InjectMocks
    PlaceService placeService;

    @Mock
    VisitedPlaceRepositoiry visitedPlaceRepositoiry;

    @Mock
    PlaceRepository placeRepository;


    @Test
    @DisplayName("특정 사용자가 장소에 몇번 방문했는지 확인할 수 있다.")
    void countVisitedPlace() throws Exception {
        // given
        BDDMockito.given(visitedPlaceRepositoiry.countByMemberIdAndPlaceId(any(), any()))
                .willReturn(10L);
        Place place = Place.builder()
                .name("테스트이름")
                .address("테스트주소")
                .shortIntroduction("테스트소개")
                .placeCategory(PlaceCategory.CULTURE)
                .placeArea(PlaceArea.AREA1)
                .offroadCode("테스트오프로드코드")
                .latitude(37.123456)
                .longitude(127.123456)
                .categoryImageUrl("테스트카테고리이미지URL")
                .build();
        // when
        Long count =  placeService.countVisitedPlace(1L, place);

        // then
        Assertions.assertThat(count).isEqualTo(10L);

    }

    @Test
    @DisplayName("장소 id로 장소를 조회할 수 있다.")
    void findById() throws Exception {
        // given
        Place place = createPlace(PlaceArea.AREA1, PlaceCategory.CULTURE, "테스트이름", "테스트주소",
                "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL");

        BDDMockito.given(placeRepository.findById(any()))
                .willReturn(Optional.of(place));

        // when
        Place expectedPlace =  placeService.findPlaceById(1L);

        // then
        Assertions.assertThat(expectedPlace).extracting(
                "placeArea",
                "placeCategory",
                "name",
                "address",
                "shortIntroduction",
                "offroadCode",
                "latitude",
                "longitude",
                "categoryImageUrl")
                .containsExactly(
                        PlaceArea.AREA1,
                        PlaceCategory.CULTURE,
                        "테스트이름",
                        "테스트주소",
                        "테스트소개",
                        "1234",
                        1234.1234,
                        234.241,
                        "테스트카테고리이미지URL");

    }

    @Test
    @DisplayName("위치 기반으로 근처에 있는 장소를 조회할 수 있다.")
    void findPlaces() throws Exception {
      // given
        Place place = createPlace(PlaceArea.AREA1, PlaceCategory.CULTURE, "테스트이름", "테스트주소",
                "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL");
        Place place2 = createPlace(PlaceArea.AREA2, PlaceCategory.CAFFE, "테스트이름2", "테스트주소2",
                "테스트소개2", "12342", 1234.12342, 234.2412, "테스트카테고리이미지URL2");

        when(placeRepository.findAllByCurrentLatitudeAndCurrentLongitude(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(List.of(place, place2));

        RegisteredPlacesRequest request = RegisteredPlacesRequest.of(32.135, 137.123);

        // when
        List<Place> placeList =  placeService.findPlaces(request);

        // then
        Assertions.assertThat(placeList)
                .extracting("placeArea", "placeCategory", "name", "address", "shortIntroduction", "offroadCode", "latitude", "longitude", "categoryImageUrl")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(PlaceArea.AREA1, PlaceCategory.CULTURE, "테스트이름", "테스트주소", "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL"),
                        Tuple.tuple(PlaceArea.AREA2, PlaceCategory.CAFFE, "테스트이름2", "테스트주소2", "테스트소개2", "12342", 1234.12342, 234.2412, "테스트카테고리이미지URL2")
                );

    }

    private Place createPlace(
            PlaceArea placeArea,
            PlaceCategory placeCategory,
            String name,
            String address,
            String shortIntroduction,
            String offroadCode,
            double latitude,
            double longitude,
            String categoryImageUrl
    ) {
        return Place.builder()
                .placeArea(placeArea)
                .placeCategory(placeCategory)
                .name(name)
                .address(address)
                .shortIntroduction(shortIntroduction)
                .offroadCode(offroadCode)
                .latitude(latitude)
                .longitude(longitude)
                .categoryImageUrl(categoryImageUrl)
                .build();
    }

}