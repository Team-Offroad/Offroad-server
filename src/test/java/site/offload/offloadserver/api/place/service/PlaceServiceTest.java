package site.offload.offloadserver.api.place.service;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import site.offload.offloadserver.db.place.entity.Place;
import site.offload.offloadserver.db.place.entity.PlaceArea;
import site.offload.offloadserver.db.place.entity.PlaceCategory;
import site.offload.offloadserver.db.place.repository.PlaceRepository;
import site.offload.offloadserver.db.place.repository.VisitedPlaceRepositoiry;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

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
        Place place = Place.builder()
                .placeArea(PlaceArea.AREA1)
                .placeCategory(PlaceCategory.CULTURE)
                .name("테스트이름")
                .address("테스트주소")
                .shortIntroduction("테스트소개")
                .offroadCode("1234")
                .latitude(1234.1234)
                .longitude(234.241)
                .categoryImageUrl("테스트카테고리이미지URL")
                .build();

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

}