package site.offload.api.place.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sites.offload.db.member.entity.MemberEntity;
import sites.offload.db.place.entity.PlaceEntity;
import sites.offload.db.place.entity.VisitedPlaceEntity;
import sites.offload.db.place.repository.VisitedPlaceRepository;

@Component
@RequiredArgsConstructor
public class VisitedPlaceService {

    private final VisitedPlaceRepository visitedPlaceRepository;

    public Long save(VisitedPlaceEntity visitedPlaceEntity) {
        return visitedPlaceRepository.save(visitedPlaceEntity).getId();
    }

    public Long countByMemberAndPlace(MemberEntity memberEntity, PlaceEntity placeEntity) {
        return visitedPlaceRepository.countByMemberEntityIdAndPlaceEntityId(memberEntity.getId(), placeEntity.getId());
    }
}
