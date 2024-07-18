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


import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @InjectMocks
    PlaceService placeService;

    @Mock
    VisitedPlaceRepositoiry visitedPlaceRepositoiry;

    @Test
    @DisplayName("")
    void test() throws Exception {
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

}