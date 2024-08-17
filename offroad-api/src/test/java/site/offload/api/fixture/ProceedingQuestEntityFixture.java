package site.offload.api.fixture;

import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.entity.ProceedingQuestEntity;
import site.offload.db.quest.entity.QuestEntity;

public class ProceedingQuestEntityFixture {

    public static ProceedingQuestEntity createProceedingQuest(MemberEntity memberEntity, QuestEntity questEntity) {
        return ProceedingQuestEntity.create(memberEntity, questEntity);
    }
}
