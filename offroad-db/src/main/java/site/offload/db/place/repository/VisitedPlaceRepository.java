package site.offload.db.place.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.place.entity.VisitedPlaceEntity;

public interface VisitedPlaceRepository extends JpaRepository<VisitedPlaceEntity, Long> {
    Long countByMemberEntityIdAndPlaceEntityId(Long memberId, Long placeId);
    Long countByMemberEntityId(Long memberId);

    void deleteAllByMemberEntityId(long memberId);
}
