package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.entity.CompleteQuestEntity;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.db.quest.repository.CompleteQuestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompleteQuestService {

    private final CompleteQuestRepository completeQuestRepository;

    public List<CompleteQuestEntity> findAllByMemberId(Long id) {
        return completeQuestRepository.findAllByMemberEntityId(id);
    }

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

    public void deleteAllByMemberId(long memberId) {
        completeQuestRepository.deleteAllByMemberEntityId(memberId);
    }

    public Long countByMember(MemberEntity entity) {
        return completeQuestRepository.countByMemberEntityId(entity.getId());
    }
}
