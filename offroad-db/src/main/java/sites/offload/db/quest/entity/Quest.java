package sites.offload.db.quest.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sites.offload.db.BaseTimeEntity;
import sites.offload.enums.PlaceArea;
import sites.offload.enums.PlaceCategory;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quest extends BaseTimeEntity {

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
}
