package site.offload.api.fixture;

import site.offload.dbjpa.quest.entity.QuestEntity;
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
