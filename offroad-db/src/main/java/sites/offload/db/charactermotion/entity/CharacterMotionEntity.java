package sites.offload.db.charactermotion.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sites.offload.db.BaseTimeEntity;
import sites.offload.db.character.entity.CharacterEntity;
import sites.offload.enums.PlaceCategory;

//각 캐릭터의 모션
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name = "character_motion",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"character_id", "placeCategory"})
})
public class CharacterMotionEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity characterEntity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceCategory placeCategory;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motionImageUrl;
}
