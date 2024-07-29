package sites.offload.db.place.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.place.entity.VisitedPlaceEntity;

public interface VisitedPlaceRepository extends CrudRepository<VisitedPlaceEntity, Long> {
    Long countByMemberEntityIdAndPlaceEntityId(Long memberId, Long placeId);
}
