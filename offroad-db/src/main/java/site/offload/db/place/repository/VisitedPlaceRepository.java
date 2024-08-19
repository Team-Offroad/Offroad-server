package site.offload.db.place.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.place.entity.PlaceEntity;
import site.offload.db.place.entity.VisitedPlaceEntity;

import java.util.List;

public interface VisitedPlaceRepository extends JpaRepository<VisitedPlaceEntity, Long> {
    Long countByMemberEntityIdAndPlaceEntityId(Long memberId, Long placeId);
    Long countByMemberEntityId(Long memberId);

    void deleteAllByMemberEntityId(long memberId);

    List<VisitedPlaceEntity> findTop100ByMemberEntityId(long memberId);

    Boolean existsByMemberEntityIdAndPlaceEntityId(Long memberId, Long placeId);
}
