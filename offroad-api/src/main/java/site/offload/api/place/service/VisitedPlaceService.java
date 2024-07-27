package site.offload.api.place.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sites.offload.db.member.entity.MemberEntity;
import sites.offload.db.place.entity.PlaceEntity;
import sites.offload.db.place.entity.VisitedPlaceEntity;
import sites.offload.db.place.repository.VisitedPlaceRepositoiry;

@Component
@RequiredArgsConstructor
public class VisitedPlaceService {

    private final VisitedPlaceRepositoiry visitedPlaceRepositoiry;

    public Long save(VisitedPlaceEntity visitedPlaceEntity) {
        return visitedPlaceRepositoiry.save(visitedPlaceEntity).getId();
    }

    public Long countByMemberAndPlace(MemberEntity memberEntity, PlaceEntity placeEntity) {
        return visitedPlaceRepositoiry.countByMemberIdAndPlaceId(memberEntity.getId(), placeEntity.getId());
    }
}
