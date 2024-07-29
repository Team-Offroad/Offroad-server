package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sites.offload.db.member.entity.MemberEntity;
import sites.offload.db.quest.entity.CompleteQuestEntity;
import sites.offload.db.quest.entity.QuestEntity;
import sites.offload.db.quest.repository.CompleteQuestRepository;

@Service
@RequiredArgsConstructor
public class CompleteQuestService {

    private final CompleteQuestRepository completeQuestRepository;

    public void saveCompleteQuest(QuestEntity questEntity, MemberEntity memberEntity) {
        completeQuestRepository.save(
                CompleteQuestEntity.builder()
                        .questEntity(questEntity)
                        .memberEntity(memberEntity)
                        .build());
    }

    public boolean isExistByQuestAndMember(QuestEntity questEntity, MemberEntity memberEntity) {
        return completeQuestRepository.existsByQuestEntityAndMemberEntity(questEntity, memberEntity);
    }
}
