package site.offload.offloadserver.db.place.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.place.entity.Place;
import site.offload.offloadserver.db.place.entity.VisitedPlace;

import java.util.List;

public interface VisitedPlaceRepositoiry extends CrudRepository<VisitedPlace, Long> {
    int countByMemberIdAndPlaceId(Long memberId, Long placeId);
}
