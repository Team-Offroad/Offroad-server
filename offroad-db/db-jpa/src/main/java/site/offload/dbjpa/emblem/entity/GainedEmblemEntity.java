package site.offload.dbjpa.emblem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.dbjpa.BaseTimeEntity;
import site.offload.dbjpa.member.entity.MemberEntity;

//사용자가 얻은 칭호
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gained_emblem")
@Getter
public class GainedEmblemEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @Column(nullable = false)
    private String emblemCode;

    private GainedEmblemEntity(MemberEntity memberEntity, String emblemCode) {
        this.memberEntity = memberEntity;
        this.emblemCode = emblemCode;
    }

    public static GainedEmblemEntity create(MemberEntity memberEntity, String emblemCode) {
        return new GainedEmblemEntity(memberEntity, emblemCode);
    }
}
