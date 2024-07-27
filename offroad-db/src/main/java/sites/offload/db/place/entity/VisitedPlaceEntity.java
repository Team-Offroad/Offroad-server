package sites.offload.db.place.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sites.offload.db.BaseTimeEntity;
import sites.offload.db.member.entity.MemberEntity;

//사용자가 방문한 장소
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "visited_place")
public class VisitedPlaceEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private PlaceEntity placeEntity;

    private VisitedPlaceEntity(MemberEntity memberEntity, PlaceEntity placeEntity) {
        this.memberEntity = memberEntity;
        this.placeEntity = placeEntity;
    }

    public static VisitedPlaceEntity create(MemberEntity memberEntity, PlaceEntity placeEntity) {
        return new VisitedPlaceEntity(memberEntity, placeEntity);
    }
}
