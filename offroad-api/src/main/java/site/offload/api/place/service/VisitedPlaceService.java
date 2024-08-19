package site.offload.api.place.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.place.entity.PlaceEntity;
import site.offload.db.place.entity.VisitedPlaceEntity;
import site.offload.db.place.repository.VisitedPlaceRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VisitedPlaceService {

    private final VisitedPlaceRepository visitedPlaceRepository;

    public Long save(VisitedPlaceEntity visitedPlaceEntity) {
        return visitedPlaceRepository.save(visitedPlaceEntity).getId();
    }

    public Long countByMemberIdAndPlace(Long memberId, PlaceEntity placeEntity) {
        return visitedPlaceRepository.countByMemberEntityIdAndPlaceEntityId(memberId, placeEntity.getId());
    }

    public Long countMemberAndPlace(MemberEntity memberEntity, PlaceEntity placeEntity) {
        return visitedPlaceRepository.countByMemberEntityIdAndPlaceEntityId(memberEntity.getId(), placeEntity.getId());
    }

    public void deleteAllByMemberId(long memberId) {
        visitedPlaceRepository.deleteAllByMemberEntityId(memberId);
    }

    public List<VisitedPlaceEntity> findAllByMemberId(long memberId) {
        return visitedPlaceRepository.findTop100ByMemberEntityId(memberId);
    }
  
    public Long countByMember(MemberEntity entity) {
        return visitedPlaceRepository.countByMemberEntityId(entity.getId());
    }

    public Boolean existsByMemberAndPlace(MemberEntity memberEntity, PlaceEntity placeEntity) {
        return visitedPlaceRepository.existsByMemberEntityIdAndPlaceEntityId(memberEntity.getId(), placeEntity.getId());
    }
}
