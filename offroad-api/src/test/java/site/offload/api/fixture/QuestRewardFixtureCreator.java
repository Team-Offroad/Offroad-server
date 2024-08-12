package site.offload.api.fixture;

import site.offload.dbjpa.quest.embeddable.RewardList;
import site.offload.dbjpa.quest.entity.QuestRewardEntity;

public class QuestRewardFixtureCreator {

    public static QuestRewardEntity createQuestReward(int questId, RewardList rewardList) {
        return QuestRewardEntity.builder()
                .questId(questId)
                .rewardList(rewardList)
                .build();
    }
}
