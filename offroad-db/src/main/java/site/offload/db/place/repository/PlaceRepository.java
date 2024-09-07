package site.offload.db.place.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.offload.db.place.entity.PlaceEntity;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {

    // 현재 latitude에서 오차 범위 -+ 0.01, 현재 longitude에서 오차 범위 -+ 0.005사이에 있는 오프로드 등록 장소들 호출
    @Query("select p from PlaceEntity p where p.latitude >= :currentLatitude - :rangeLatitude and p.latitude <= :currentLatitude + :rangeLatitude and p.longitude >= :currentLongitude - :rangeLongitude and p.longitude <= :currentLongitude + :rangeLongitude")
    List<PlaceEntity> findAllByCurrentLatitudeAndCurrentLongitude(@Param("currentLatitude") double currentLatitude, @Param("currentLongitude") double currentLongitude, @Param("rangeLatitude") double rangeLatitude, @Param("rangeLongitude") double rangeLongitude);

    Optional<PlaceEntity> findByCouponAuthCode(String couponAuthCode);


    @Query(value = "SELECT * FROM place p ORDER BY ST_Distance_Sphere(POINT(p.longitude, p.latitude), POINT(:longitude, :latitude))", nativeQuery = true)
    List<PlaceEntity> findNearestPlacesByLatitudeAndLongitude(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            Pageable pageable
    );

}
