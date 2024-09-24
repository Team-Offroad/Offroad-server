package site.offload.api.place.service;


import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.place.dto.request.RegisteredPlacesRequest;
import site.offload.db.place.entity.PlaceEntity;
import site.offload.db.place.repository.PlaceRepository;
import site.offload.db.place.repository.VisitedPlaceRepository;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static site.offload.api.fixture.PlaceEntityFixtureCreator.createPlace;

@ExtendWith(MockitoExtension.class)
class PlaceEntityServiceTest {

    @InjectMocks
    PlaceService placeService;

    @Mock
    VisitedPlaceRepository visitedPlaceRepository;

    @Mock
    PlaceRepository placeRepository;


    @Test
    @DisplayName("특정 사용자가 장소에 몇번 방문했는지 확인할 수 있다.")
    void countVisitedPlace() throws Exception {
        // given
        BDDMockito.given(visitedPlaceRepository.countByMemberEntityIdAndPlaceEntityId(any(), any())).willReturn(10L);
        PlaceEntity placeEntity = PlaceEntity.builder().name("테스트이름").address("테스트주소").shortIntroduction("테스트소개").placeCategory(PlaceCategory.CULTURE).placeArea(PlaceArea.AREA1).offroadCode("테스트오프로드코드").latitude(37.123456).longitude(127.123456).categoryImageUrl("테스트카테고리이미지URL").build();
        // when
        Long count = placeService.countVisitedPlace(1L, placeEntity);

        // then
        Assertions.assertThat(count).isEqualTo(10L);

    }

    @Test
    @DisplayName("장소 id로 장소를 조회할 수 있다.")
    void findById() throws Exception {
        // given
        PlaceEntity placeEntity = createPlace(PlaceArea.AREA1, PlaceCategory.CULTURE, "테스트이름", "테스트주소", "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL", "테스트쿠폰사용코드");

        BDDMockito.given(placeRepository.findById(any())).willReturn(Optional.of(placeEntity));

        // when
        PlaceEntity expectedPlaceEntity = placeService.findPlaceById(1L);

        // then
        Assertions.assertThat(expectedPlaceEntity).extracting("placeArea", "placeCategory", "name", "address", "shortIntroduction", "offroadCode", "latitude", "longitude", "categoryImageUrl").containsExactly(PlaceArea.AREA1, PlaceCategory.CULTURE, "테스트이름", "테스트주소", "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL");

    }

    @Test
    @DisplayName("위치 기반으로 근처에 있는 장소를 조회할 수 있다.")
    void findPlaces() throws Exception {
        // given
        PlaceEntity placeEntity = createPlace(PlaceArea.AREA1, PlaceCategory.CULTURE, "테스트이름", "테스트주소", "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL", "테스트쿠폰사용코드");
        PlaceEntity placeEntity2 = createPlace(PlaceArea.AREA2, PlaceCategory.CAFFE, "테스트이름2", "테스트주소2", "테스트소개2", "12342", 1234.12342, 234.2412, "테스트카테고리이미지URL2", "테스트쿠폰사용코드");

        when(placeRepository.findAllByCurrentLatitudeAndCurrentLongitude(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(List.of(placeEntity, placeEntity2));

        RegisteredPlacesRequest request = RegisteredPlacesRequest.of(32.135, 137.123, true);

        // when
        List<PlaceEntity> placeEntityList = placeService.findPlaces(request);

        // then
        Assertions.assertThat(placeEntityList).extracting("placeArea", "placeCategory", "name", "address", "shortIntroduction", "offroadCode", "latitude", "longitude", "categoryImageUrl").containsExactlyInAnyOrder(Tuple.tuple(PlaceArea.AREA1, PlaceCategory.CULTURE, "테스트이름", "테스트주소", "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL"), Tuple.tuple(PlaceArea.AREA2, PlaceCategory.CAFFE, "테스트이름2", "테스트주소2", "테스트소개2", "12342", 1234.12342, 234.2412, "테스트카테고리이미지URL2"));
    }

    @Test
    @DisplayName("쿠폰 사용 코드로 장소를 조회할 수 있다")
    void findByCouponAuthCode() {
        //given
        PlaceEntity placeEntity = createPlace(PlaceArea.AREA1, PlaceCategory.CULTURE, "테스트이름", "테스트주소", "테스트소개", "1234", 1234.1234, 234.241, "테스트카테고리이미지URL", "테스트쿠폰사용코드");
        BDDMockito.given(placeRepository.findByCouponAuthCode(BDDMockito.anyString())).willReturn(Optional.ofNullable(placeEntity));

        //when
        PlaceEntity expectedPlaceEntity = placeService.findByCouponAuthCode("테스트쿠폰사용코드");

        //then
        Assertions.assertThat(expectedPlaceEntity).isEqualTo(placeEntity);
    }

    @Test
    @DisplayName("쿠폰 사용 코드에 해당하는 장소가 존재하는지 확인할 수 있다")
    void checkExistByCouponAuthCode() {

        //given
        BDDMockito.given(placeRepository.existsByCouponAuthCode(BDDMockito.anyString())).willReturn(true);

        //when
        boolean expected = placeService.isExistByCouponAuthCode("example");

        //then
        Assertions.assertThat(expected).isEqualTo(true);
    }
}