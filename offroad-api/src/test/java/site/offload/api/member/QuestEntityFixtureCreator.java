package site.offload.api.member;

import site.offload.db.quest.entity.QuestEntity;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

public class QuestEntityFixtureCreator {

    public static QuestEntity createQuest(
            String name,
            PlaceCategory placeCategory,
            PlaceArea placeArea,
            boolean isQuestSamePlace,
            int totalRequiredClearCount) {
        return QuestEntity.builder()
                .name(name)
                .placeCategory(placeCategory)
                .placeArea(placeArea)
                .isQuestSamePlace(isQuestSamePlace)
                .totalRequiredClearCount(totalRequiredClearCount)
                .build();
    }
}
