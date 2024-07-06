package site.offload.offloadserver.db.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.entity.Place;

public interface PlaceRepository extends CrudRepository<Place, Integer> {
}
