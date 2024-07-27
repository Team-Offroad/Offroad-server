package sites.offload.db.place.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import sites.offload.db.place.entity.PlaceEntity;

import java.util.List;

public interface PlaceRepository extends CrudRepository<PlaceEntity, Long> {

    // 현재 latitude에서 오차 범위 -+ 0.01, 현재 longitude에서 오차 범위 -+ 0.005사이에 있는 오프로드 등록 장소들 호출
    @Query("select p from PlaceEntity p where p.latitude >= :currentLatitude - :rangeLatitude and p.latitude <= :currentLatitude + :rangeLatitude and p.longitude >= :currentLongitude - :rangeLongitude and p.longitude <= :currentLongitude + :rangeLongitude")
    List<PlaceEntity> findAllByCurrentLatitudeAndCurrentLongitude(@Param("currentLatitude") double currentLatitude, @Param("currentLongitude") double currentLongitude, @Param("rangeLatitude") double rangeLatitude, @Param("rangeLongitude") double rangeLongitude);
}
