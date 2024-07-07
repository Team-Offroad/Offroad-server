package site.offload.offloadserver.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

//각 캐릭터의 모션
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String motionImageUrl;

    @Builder
    private CharacterMotion(Character character, PlaceCategory placeCategory, String motionImageUrl) {
        this.character = character;
        this.placeCategory = placeCategory;
        this.motionImageUrl = motionImageUrl;
    }
}
