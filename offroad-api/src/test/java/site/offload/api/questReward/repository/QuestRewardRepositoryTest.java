package site.offload.api.questReward.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.auth.jwt.JwtTokenProvider;
import site.offload.api.member.usecase.SocialLoginUseCase;
import site.offload.db.quest.embeddable.RewardList;
import site.offload.db.quest.entity.QuestRewardEntity;
import site.offload.db.quest.repository.QuestRewardRepository;
import site.offload.external.oauth.apple.AppleSocialLoginService;
import site.offload.external.oauth.google.GoogleSocialLoginService;

import java.util.List;

@DataJpaTest
public class QuestRewardRepositoryTest {

    @Autowired
    QuestRewardRepository questRewardRepository;

    @MockBean
    ObjectMapper objectMapper;

    @MockBean
    GoogleSocialLoginService googleSocialLoginService;

    @MockBean
    AppleSocialLoginService appleSocialLoginService;


    @Test
    @Transactional
    @DisplayName("보상이 칭호인 퀘스트 보상 엔티티를 불러올 수 있다.")
    void findQuestRewardEntitiesWithEmblems() {

        //given

        QuestRewardEntity questRewardEntity1 = createQuestRewardEntity(1, createRewardList(false, null, "emblemCode1"));
        QuestRewardEntity questRewardEntity2 = createQuestRewardEntity(2, createRewardList(false, "couponCode2", null));
        QuestRewardEntity questRewardEntity3 = createQuestRewardEntity(3, createRewardList(false, "couponCode3", null));
        QuestRewardEntity questRewardEntity4 = createQuestRewardEntity(4, createRewardList(false, null, "emblemCode4"));
        QuestRewardEntity questRewardEntity5 = createQuestRewardEntity(5, createRewardList(false, null, "emblemCode5"));


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

        System.out.println("emblems = " + emblems);
        Assertions.assertThat(emblems).contains(questRewardEntity1.getRewardList().getEmblemCode());
        Assertions.assertThat(emblems).contains(questRewardEntity4.getRewardList().getEmblemCode());
        Assertions.assertThat(emblems.size()).isEqualTo(3);


        //이게 잘 동작되지 않는 이유는 저장과 찾을 때의 Entity가 서로 다르기 때문인가요?
        // -> 쿼리문으로 실제 DB에서 찾아오기 때문에 영속성 Context에서 불러오지 않음. 즉, 새로운 다른 객체를 불러오는 것 같음
        Assertions.assertThat(questRewardEntities).contains(questRewardEntity1);
        Assertions.assertThat(questRewardEntities).contains(questRewardEntity4);
    }

    RewardList createRewardList(boolean isCharacterMotion, String couponCode, String emblemCode) {
        return new RewardList(isCharacterMotion, couponCode, emblemCode);
    }

    QuestRewardEntity createQuestRewardEntity(int questId, RewardList rewardList) {
        return QuestRewardEntity.builder()
                .questId(questId)
                .rewardList(rewardList)
                .build();
    }
}
