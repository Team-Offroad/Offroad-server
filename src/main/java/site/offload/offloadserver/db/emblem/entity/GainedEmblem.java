package site.offload.offloadserver.db.emblem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.member.entity.Member;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Emblem emblemName;
}
