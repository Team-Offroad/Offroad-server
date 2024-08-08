package site.offload.api.questReward.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.quest.service.QuestRewardService;
import site.offload.api.questReward.QuestRewardFixtureCreator;
import site.offload.db.quest.entity.QuestRewardEntity;
import site.offload.db.quest.repository.QuestRewardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static site.offload.api.questReward.QuestRewardFixtureCreator.*;
import static site.offload.api.questReward.RewardListFixtureCreator.createRewardList;


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

        QuestRewardEntity questRewardEntity1 = createQuestReward(1, createRewardList(false, null, "emblemCode1", false));
        QuestRewardEntity questRewardEntity4 = createQuestReward(4, createRewardList(false, null, "emblemCode4", false));

        List<QuestRewardEntity> questRewardEntitiesWithEmblems = new ArrayList<QuestRewardEntity>();
        questRewardEntitiesWithEmblems.add(questRewardEntity1);
        questRewardEntitiesWithEmblems.add(questRewardEntity4);

        given(questRewardRepository.findAllWithEmblems())
                .willReturn(questRewardEntitiesWithEmblems);

        //when

        List<QuestRewardEntity> expect = questRewardService.findQuestWithEmblems();

        //then

        Assertions.assertThat(expect).isEqualTo(questRewardEntitiesWithEmblems);

    }

    @Test
    @DisplayName("쿠폰 코드로 퀘스트 보상을 조회할 수 있다")
    void findQuestRewardByCouponCode() {
        //given
        QuestRewardEntity questRewardEntity1 = createQuestReward(1, createRewardList(false, "couponCode", "emblemCode1", false));
        given(questRewardRepository.findByCouponCode(anyString())).willReturn(Optional.ofNullable(questRewardEntity1));

        //when
        QuestRewardEntity expectedQuestRewardEntity = questRewardService.findByCouponCode("couponCode");

        //then
        Assertions.assertThat(expectedQuestRewardEntity).isEqualTo(questRewardEntity1);
    }
}
