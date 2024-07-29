package site.offload.db.character.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.db.BaseTimeEntity;

//캐릭터
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "characters")
public class CharacterEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String characterBaseImageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String characterSpotLightImageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String characterAdventureSuccessImageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String characterAdventureQRFailureImageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String characterAdventureLocationFailureImageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String characterSelectImageUrl;

    @Column(nullable = false, unique = true)
    private String CharacterCode;
}
