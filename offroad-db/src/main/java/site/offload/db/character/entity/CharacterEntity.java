package site.offload.db.character.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.db.BaseTimeEntity;
import site.offload.enums.place.PlaceArea;

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

    @Column(nullable = false)
    private String summaryDescription;

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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String notGainedCharacterThumbnailImageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String characterIconImageUrl;

    private PlaceArea placeArea;

    @Column(nullable = false)
    private String characterMainColorCode;

    @Column(nullable = false)
    private String characterSubColorCode;

    @Builder
    public CharacterEntity(String name, String description, String characterBaseImageUrl, String characterSpotLightImageUrl, String characterAdventureSuccessImageUrl, String characterAdventureQRFailureImageUrl, String characterAdventureLocationFailureImageUrl, String characterSelectImageUrl, String characterCode, String notGainedCharacterThumbnailImageUrl, String characterMainColorCode, String characterSubColorCode, String summaryDescription, String characterIconImageUrl) {
        this.name = name;
        this.description = description;
        this.characterBaseImageUrl = characterBaseImageUrl;
        this.characterSpotLightImageUrl = characterSpotLightImageUrl;
        this.characterAdventureSuccessImageUrl = characterAdventureSuccessImageUrl;
        this.characterAdventureQRFailureImageUrl = characterAdventureQRFailureImageUrl;
        this.characterAdventureLocationFailureImageUrl = characterAdventureLocationFailureImageUrl;
        this.characterSelectImageUrl = characterSelectImageUrl;
        this.CharacterCode = characterCode;
        this.notGainedCharacterThumbnailImageUrl = notGainedCharacterThumbnailImageUrl;
        this.characterMainColorCode = characterMainColorCode;
        this.characterSubColorCode = characterSubColorCode;
        this.characterIconImageUrl = characterIconImageUrl;
        this.summaryDescription = summaryDescription;
    }
}
