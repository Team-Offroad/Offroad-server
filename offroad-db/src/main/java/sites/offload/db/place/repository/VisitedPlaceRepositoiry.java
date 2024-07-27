package sites.offload.db.place.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.place.entity.VisitedPlaceEntity;

public interface VisitedPlaceRepositoiry extends CrudRepository<VisitedPlaceEntity, Long> {
    Long countByMemberIdAndPlaceId(Long memberId, Long placeId);
}
