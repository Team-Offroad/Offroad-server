package site.offload.api.questReward.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import site.offload.db.config.JpaAuditingConfig;
import site.offload.db.quest.entity.QuestRewardEntity;
import site.offload.db.quest.repository.QuestRewardRepository;

import java.util.List;

import static site.offload.api.fixture.QuestRewardFixtureCreator.createQuestReward;
import static site.offload.api.fixture.RewardListFixtureCreator.createRewardList;

@DataJpaTest
@Import(JpaAuditingConfig.class)
public class QuestRewardRepositoryTest {

    @Autowired
    QuestRewardRepository questRewardRepository;

    @Test
    @DisplayName("보상이 칭호인 퀘스트 보상 엔티티를 불러올 수 있다.")
    void findQuestRewardEntitiesWithEmblems() {

        //given

        QuestRewardEntity questRewardEntity1 = createQuestReward(1, createRewardList(false, null, "emblemCode1", false));
        QuestRewardEntity questRewardEntity2 = createQuestReward(2, createRewardList(false, "couponCode2", null, false));
        QuestRewardEntity questRewardEntity3 = createQuestReward(3, createRewardList(false, "couponCode3", null, false));
        QuestRewardEntity questRewardEntity4 = createQuestReward(4, createRewardList(false, null, "emblemCode4", false));
        QuestRewardEntity questRewardEntity5 = createQuestReward(5, createRewardList(false, null, "emblemCode5", false));


        questRewardRepository.save(questRewardEntity1);
        questRewardRepository.save(questRewardEntity2);
        questRewardRepository.save(questRewardEntity3);
        questRewardRepository.save(questRewardEntity4);
        questRewardRepository.save(questRewardEntity5);


        //when
        List<QuestRewardEntity> questRewardEntities = questRewardRepository.findAllWithEmblems();
        List<String> emblems = questRewardEntities.stream()
                .map(questRewardEntity -> questRewardEntity.getRewardList().getEmblemCode())
                .toList();

        //then
        Assertions.assertThat(emblems).contains(questRewardEntity1.getRewardList().getEmblemCode());
        Assertions.assertThat(emblems).contains(questRewardEntity4.getRewardList().getEmblemCode());
        Assertions.assertThat(emblems.size()).isEqualTo(3);


        //이게 잘 동작되지 않는 이유는 저장과 찾을 때의 Entity가 서로 다르기 때문인가요?
        // -> 쿼리문으로 실제 DB에서 찾아오기 때문에 영속성 Context에서 불러오지 않음. 즉, 새로운 다른 객체를 불러오는 것 같음
        Assertions.assertThat(questRewardEntities).contains(questRewardEntity1);
        Assertions.assertThat(questRewardEntities).contains(questRewardEntity4);
    }
}
