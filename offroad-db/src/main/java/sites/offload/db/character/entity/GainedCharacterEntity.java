package sites.offload.db.character.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import sites.offload.db.BaseTimeEntity;
import sites.offload.db.member.entity.MemberEntity;

//사용자가 얻은 캐릭터
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gained_character",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "character_id"})
})
public class GainedCharacterEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity memberEntity;

    @JoinColumn(name = "character_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CharacterEntity characterEntity;

    @Builder
    public GainedCharacterEntity(MemberEntity memberEntity, CharacterEntity characterEntity) {
        this.memberEntity = memberEntity;
        this.characterEntity = characterEntity;
    }
}
