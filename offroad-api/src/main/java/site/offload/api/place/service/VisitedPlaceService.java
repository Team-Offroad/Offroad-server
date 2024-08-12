package site.offload.api.place.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.dbjpa.member.entity.MemberEntity;
import site.offload.dbjpa.place.entity.PlaceEntity;
import site.offload.dbjpa.place.entity.VisitedPlaceEntity;
import site.offload.dbjpa.place.repository.VisitedPlaceRepository;

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

    public void deleteAllByMemberId(long memberId) {
        visitedPlaceRepository.deleteAllByMemberEntityId(memberId);
    }
}
