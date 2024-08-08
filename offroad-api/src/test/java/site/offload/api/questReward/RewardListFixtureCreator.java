package site.offload.api.questReward;

import site.offload.db.quest.embeddable.RewardList;

public class RewardListFixtureCreator {

    public static RewardList createRewardList(boolean isCharacterMotion, String couponCode, String emblemCode, boolean isCharacter) {
        return new RewardList(isCharacterMotion, couponCode, emblemCode, isCharacter);
    }
}

