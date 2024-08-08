package site.offload.api.questReward.usecase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.character.service.GainedCharacterService;
import site.offload.api.emblem.dto.response.EmblemResponse;
import site.offload.api.emblem.dto.response.EmblemsResponse;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.emblem.usecase.EmblemUseCase;
import site.offload.api.quest.service.QuestRewardService;
import site.offload.api.quest.service.QuestService;
import site.offload.db.emblem.entity.GainedEmblemEntity;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.embeddable.RewardList;
import site.offload.db.quest.entity.QuestRewardEntity;
import site.offload.enums.emblem.Emblem;
import site.offload.enums.member.SocialPlatform;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class QuestRewardUseCaseTest {

    @Mock
    QuestRewardService questRewardService;

    @Mock
    GainedEmblemService gainedEmblemService;



    @Test
    @DisplayName("보유 및 미보유 엠블렘을 구별할 수 있다.")
    void distinctGainedAndNotGainedEmblems(){

        //given

        MemberEntity memberEntity = MemberEntity.builder().name("이름1").email("이메일1").sub("소셜아이디1").socialPlatform(SocialPlatform.GOOGLE).build();

        QuestRewardEntity questRewardEntity1 = createQuestRewardEntity(1, createRewardList(false, null, "emblemCode1",false));
        QuestRewardEntity questRewardEntity2 = createQuestRewardEntity(2, createRewardList(false, null, "emblemCode2",false));
        QuestRewardEntity questRewardEntity3 = createQuestRewardEntity(3, createRewardList(false, null, "emblemCode3",false));

        List<QuestRewardEntity> questRewardEntitiesWithEmblems = new ArrayList<QuestRewardEntity>();

        questRewardEntitiesWithEmblems.add(questRewardEntity1);
        questRewardEntitiesWithEmblems.add(questRewardEntity2);
        questRewardEntitiesWithEmblems.add(questRewardEntity3);

        List<GainedEmblemEntity> gainedEmblemEntities = new ArrayList<GainedEmblemEntity>();
        gainedEmblemEntities.add(GainedEmblemEntity.create(memberEntity, "emblemCode1"));

        //when

        List<String> emblemCodes = gainedEmblemEntities.stream()
                .map(GainedEmblemEntity::getEmblemCode)
                .toList();

        List<String> gainedEmblems = questRewardEntitiesWithEmblems.stream()
                .filter(questRewardEntityWithEmblem -> emblemCodes.contains(questRewardEntityWithEmblem.getRewardList().getEmblemCode()))
                .map(questRewardEntity -> questRewardEntity.getRewardList().getEmblemCode())
                .toList();

        List<String> notGainedEmblems = questRewardEntitiesWithEmblems.stream()
                .filter(questRewardEntityWithEmblem -> !emblemCodes.contains(questRewardEntityWithEmblem.getRewardList().getEmblemCode()))
                .map(questRewardEntity -> questRewardEntity.getRewardList().getEmblemCode())
                .toList();

        //then
        Assertions.assertThat(gainedEmblems).contains(questRewardEntity1.getRewardList().getEmblemCode());
        Assertions.assertThat(notGainedEmblems).contains(questRewardEntity2.getRewardList().getEmblemCode());
        Assertions.assertThat(notGainedEmblems).contains(questRewardEntity3.getRewardList().getEmblemCode());

    }

    RewardList createRewardList(boolean isCharacterMotion, String couponCode, String emblemCode, boolean isCharacter) {
        return new RewardList(isCharacterMotion, couponCode, emblemCode, isCharacter);
    }

    QuestRewardEntity createQuestRewardEntity(int questId, RewardList rewardList){
        return QuestRewardEntity.builder()
                .questId(questId)
                .rewardList(rewardList)
                .build();
    }
}
