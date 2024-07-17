package site.offload.offloadserver.db.charactermotion.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.BaseTimeEntity;
import site.offload.offloadserver.db.member.entity.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "character_motion_id"})
})
public class GainedCharacterMotion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_motion_id", nullable = false)
    private CharacterMotion characterMotion;

    @Builder
    public GainedCharacterMotion(Member member, CharacterMotion characterMotion){
        this.member = member;
        this.characterMotion = characterMotion;
    }
}
