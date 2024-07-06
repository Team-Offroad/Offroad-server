package site.offload.offloadserver.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

//사용자가 방문한 장소
@Entity
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

    //사용자가 방문한 장소이므로, 기본값을 1로 설정
    @Column(columnDefinition = "integer CHECK (visit_count >= 1)")
    private int visitCount = 1;

    @Builder
    private VisitedPlace(Member member, Place place) {
        this.member = member;
        this.place = place;
    }
}
