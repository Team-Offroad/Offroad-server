package site.offload.dbjpa.place.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.dbjpa.place.entity.VisitedPlaceEntity;

public interface VisitedPlaceRepository extends JpaRepository<VisitedPlaceEntity, Long> {
    Long countByMemberEntityIdAndPlaceEntityId(Long memberId, Long placeId);

    void deleteAllByMemberEntityId(long memberId);
}
