package site.offload.api.place.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sites.offload.db.member.entity.Member;
import sites.offload.db.place.entity.Place;
import sites.offload.db.place.entity.VisitedPlace;
import sites.offload.db.place.repository.VisitedPlaceRepositoiry;

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
