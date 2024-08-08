package site.offload.api.coupon;

import site.offload.db.quest.embeddable.RewardList;
import site.offload.db.quest.entity.QuestRewardEntity;

public class QuestRewardFixtureCreator {

    public static QuestRewardEntity createQuestReward(int questId, RewardList rewardList) {
        return QuestRewardEntity.builder()
                .questId(questId)
                .rewardList(rewardList)
                .build();
    }
}
