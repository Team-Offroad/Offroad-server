package site.offload.offloadserver.db.character.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.api.member.dto.request.SocialPlatform;
import site.offload.offloadserver.db.BaseTimeEntity;
import site.offload.offloadserver.db.member.entity.Member;

//사용자가 얻은 캐릭터
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "character_id"})
})
public class GainedCharacter extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "character_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Character character;

    @Builder
    public GainedCharacter(Member member, Character character) {
        this.member = member;
        this.character = character;
    }
}
