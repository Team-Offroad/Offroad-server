package site.offload.db.emblem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.db.BaseTimeEntity;
import site.offload.db.member.entity.MemberEntity;

//사용자가 얻은 칭호
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "gained_emblem",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "emblem_code"})
        })
public class GainedEmblemEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @Column(nullable = false)
    private String emblemCode;

    private boolean isNewGained = true;

    @Builder
    private GainedEmblemEntity(MemberEntity memberEntity, String emblemCode) {
        this.memberEntity = memberEntity;
        this.emblemCode = emblemCode;
    }

    public static GainedEmblemEntity create(MemberEntity memberEntity, String emblemCode) {
        return new GainedEmblemEntity(memberEntity, emblemCode);
    }

    public void updateNewGainedStatus() {
        this.isNewGained = false;
    }
}
