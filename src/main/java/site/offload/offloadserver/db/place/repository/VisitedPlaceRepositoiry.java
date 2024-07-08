package site.offload.offloadserver.db.place.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.place.entity.VisitedPlace;

public interface VisitedPlaceRepositoiry extends CrudRepository<VisitedPlace, Long> {
}
