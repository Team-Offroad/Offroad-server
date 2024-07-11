package site.offload.offloadserver.db.place.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import site.offload.offloadserver.db.place.entity.Place;

import java.util.List;

public interface PlaceRepository extends CrudRepository<Place, Long> {

    @Query("select p from Place p where p.latitude >= :currentLatitude - 0.01 and p.latitude <= :currentLatitude + 0.01 and p.longitude >= :currentLongitude - 0.005 and p.longitude <= :currentLongitude + 0.005")
    List<Place> findAllByCurrentLatitudeAndCurrentLongitude(@Param("currentLatitude") float currentLatitude, @Param("currentLongitude") float currentLongitude);
}
