package site.offload.api.fixture;

import site.offload.dbjpa.quest.embeddable.RewardList;

public class RewardListFixtureCreator {

    public static RewardList createRewardList(boolean isCharacterMotion, String couponCode, String emblemCode, boolean isCharacter) {
        return new RewardList(isCharacterMotion, couponCode, emblemCode, isCharacter);
    }
}

