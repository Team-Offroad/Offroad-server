package site.offload.api.questReward.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.quest.service.QuestRewardService;
import site.offload.db.quest.embeddable.RewardList;
import site.offload.db.quest.entity.QuestRewardEntity;
import site.offload.db.quest.repository.QuestRewardRepository;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class QuestRewardServiceTest {

    @InjectMocks
    QuestRewardService questRewardService;

    @Mock
    QuestRewardRepository questRewardRepository;

    @Test
    @DisplayName("보상이 칭호인 퀘스트 보상 엔티티들을 반환할 수 있다.")
    void findQuestRewardEntitiesWithEmblems() {

        //given

        QuestRewardEntity questRewardEntity1 = createQuestRewardEntity(1, createRewardList(false, null, "emblemCode1"));
        QuestRewardEntity questRewardEntity4 = createQuestRewardEntity(4, createRewardList(false, null, "emblemCode4"));

        List<QuestRewardEntity> questRewardEntitiesWithEmblems = new ArrayList<QuestRewardEntity>();
        questRewardEntitiesWithEmblems.add(questRewardEntity1);
        questRewardEntitiesWithEmblems.add(questRewardEntity4);

        BDDMockito.given(questRewardRepository.findAllWithEmblems())
                .willReturn(questRewardEntitiesWithEmblems);

        //when

        List<QuestRewardEntity> expect = questRewardService.findQuestWithEmblems();

        //then

        Assertions.assertThat(expect).isEqualTo(questRewardEntitiesWithEmblems);

    }

    RewardList createRewardList(boolean isCharacterMotion, String couponCode, String emblemCode) {
        return new RewardList(isCharacterMotion, couponCode, emblemCode);
    }

    QuestRewardEntity createQuestRewardEntity(int questId, RewardList rewardList){
        return QuestRewardEntity.builder()
                .questId(questId)
                .rewardList(rewardList)
                .build();
    }
}
