package site.offload.offloadserver.db.emblem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.BaseTimeEntity;
import site.offload.offloadserver.db.member.entity.Member;

//사용자가 얻은 칭호
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "emblem_code"})
})
@Getter
public class GainedEmblem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String emblemCode;

    private GainedEmblem(Member member, String emblemCode) {
        this.member = member;
        this.emblemCode = emblemCode;
    }

    public static GainedEmblem create(Member member, String emblemCode) {
        return new GainedEmblem(member, emblemCode);
    }
}
