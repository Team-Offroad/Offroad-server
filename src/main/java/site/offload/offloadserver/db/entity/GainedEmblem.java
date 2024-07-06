package site.offload.offloadserver.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

//사용자가 얻은 칭호
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GainedEmblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Enumerated(EnumType.STRING)
    private Emblem emblemName;

    @Builder
    private GainedEmblem(Member member, Emblem emblemName) {
        this.member = member;
        this.emblemName = emblemName;
    }
}
