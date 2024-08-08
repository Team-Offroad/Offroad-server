package site.offload.api.quest;

import site.offload.db.quest.entity.QuestEntity;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

public class QuestEntityFixtureCreator {

    public static QuestEntity createQuest(boolean isQuestSamePlace, String name, PlaceCategory placeCategory, PlaceArea placeArea, int totalRequiredClearCount) {
        return QuestEntity.builder()
                .isQuestSamePlace(isQuestSamePlace)
                .name(name)
                .placeCategory(placeCategory)
                .placeArea(placeArea)
                .totalRequiredClearCount(totalRequiredClearCount)
                .build();
    }
}
