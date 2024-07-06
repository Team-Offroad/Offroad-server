package site.offload.offloadserver.db.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.entity.VisitedPlace;

public interface VisitedPlaceRepositoiry extends CrudRepository<VisitedPlace, Integer> {
}
