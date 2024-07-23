package sites.offload.db.place.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.place.entity.VisitedPlace;

public interface VisitedPlaceRepositoiry extends CrudRepository<VisitedPlace, Long> {
    Long countByMemberIdAndPlaceId(Long memberId, Long placeId);
}
