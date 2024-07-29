package site.offload.db.charactermotion.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.offload.db.BaseTimeEntity;
import site.offload.db.member.entity.MemberEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "character_motion_id"})
})
public class GainedCharacterMotionEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_motion_id", nullable = false)
    private CharacterMotionEntity characterMotionEntity;

    @Builder
    public GainedCharacterMotionEntity(MemberEntity memberEntity, CharacterMotionEntity characterMotionEntity) {
        this.memberEntity = memberEntity;
        this.characterMotionEntity = characterMotionEntity;
    }
}
