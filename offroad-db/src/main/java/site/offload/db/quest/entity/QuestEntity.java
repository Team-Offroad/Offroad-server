package site.offload.db.quest.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.db.BaseTimeEntity;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "quest")
public class QuestEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private PlaceCategory placeCategory;

    @Enumerated(EnumType.STRING)
    private PlaceArea placeArea;

    private boolean isQuestSamePlace = false;

    @Column(nullable = false)
    private int totalRequiredClearCount;

    @Builder
    public QuestEntity(String name, PlaceCategory placeCategory, PlaceArea placeArea, boolean isQuestSamePlace, int totalRequiredClearCount) {
        this.name = name;
        this.placeCategory = placeCategory;
        this.placeArea = placeArea;
        this.isQuestSamePlace = isQuestSamePlace;
        this.totalRequiredClearCount = totalRequiredClearCount;
    }
}
