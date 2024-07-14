package site.offload.offloadserver.db.place.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.member.entity.Member;

//사용자가 방문한 장소
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisitedPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    private VisitedPlace(Member member, Place place) {
        this.member = member;
        this.place = place;
    }

    public static VisitedPlace create(Member member, Place place) {
        return new VisitedPlace(member, place);
    }
}
