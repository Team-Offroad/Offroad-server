package site.offload.offloadserver.api.place.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.place.entity.Place;
import site.offload.offloadserver.db.place.entity.VisitedPlace;
import site.offload.offloadserver.db.place.repository.VisitedPlaceRepositoiry;

@Component
@RequiredArgsConstructor
public class VisitedPlaceService {

    private final VisitedPlaceRepositoiry visitedPlaceRepositoiry;

    public Long save(VisitedPlace visitedPlace) {
        return visitedPlaceRepositoiry.save(visitedPlace).getId();
    }

    public Long countByMemberAndPlace(Member member, Place place) {
        return visitedPlaceRepositoiry.countByMemberIdAndPlaceId(member.getId(), place.getId());
    }
}
