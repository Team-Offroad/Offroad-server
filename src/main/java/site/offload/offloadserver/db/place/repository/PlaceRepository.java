package site.offload.offloadserver.db.place.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.place.entity.Place;

public interface PlaceRepository extends CrudRepository<Place, Long> {
}
