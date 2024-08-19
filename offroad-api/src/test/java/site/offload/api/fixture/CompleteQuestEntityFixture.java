package site.offload.api.fixture;

import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.entity.CompleteQuestEntity;
import site.offload.db.quest.entity.QuestEntity;

public class CompleteQuestEntityFixture {

    public static CompleteQuestEntity createCompleteQuestEntity(MemberEntity memberEntity, QuestEntity questEntity) {
        return CompleteQuestEntity.builder()
                .questEntity(questEntity)
                .memberEntity(memberEntity)
                .build();
    }
}
