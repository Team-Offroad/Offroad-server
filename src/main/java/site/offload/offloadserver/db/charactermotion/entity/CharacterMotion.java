package site.offload.offloadserver.db.charactermotion.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.character.entity.Character;
import site.offload.offloadserver.db.BaseTimeEntity;
import site.offload.offloadserver.db.place.entity.PlaceCategory;

//각 캐릭터의 모션
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"character_id", "placeCategory"})
})
public class CharacterMotion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceCategory placeCategory;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motionImageUrl;
}
