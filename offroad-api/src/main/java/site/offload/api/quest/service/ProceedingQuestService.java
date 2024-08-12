package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.api.exception.NotFoundException;
import site.offload.dbjpa.member.entity.MemberEntity;
import site.offload.dbjpa.quest.entity.ProceedingQuestEntity;
import site.offload.dbjpa.quest.entity.QuestEntity;
import site.offload.dbjpa.quest.repository.ProceedingQuestRepository;
import site.offload.enums.response.ErrorMessage;

@Component
@RequiredArgsConstructor
public class ProceedingQuestService {

    private final ProceedingQuestRepository proceedingQuestRepository;

    public ProceedingQuestEntity save(ProceedingQuestEntity proceedingQuestEntity) {
        return proceedingQuestRepository.save(proceedingQuestEntity);
    }

    public boolean existsByMemberAndQuest(MemberEntity memberEntity, QuestEntity questEntity) {
        return proceedingQuestRepository.existsByMemberEntityAndQuestEntity(memberEntity, questEntity);
    }

    public ProceedingQuestEntity findById(Long id) {
        return proceedingQuestRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PROCEEDING_QUEST_NOTFOUND_EXCEPTION)
        );
    }

    public ProceedingQuestEntity findByMemberAndQuest(MemberEntity memberEntity, QuestEntity questEntity) {
        return proceedingQuestRepository.findByMemberEntityAndQuestEntity(memberEntity, questEntity).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PROCEEDING_QUEST_NOTFOUND_EXCEPTION)
        );
    }

    public void addCurrentClearCount(ProceedingQuestEntity proceedingQuestEntity) {
        proceedingQuestEntity.addCurrentClearCount();
    }

    public void updateCurrentClearCount(ProceedingQuestEntity proceedingQuestEntity, int count) {
        proceedingQuestEntity.updateCurrentClearCount(count);
    }

    public void deleteProceedingQuest(QuestEntity questEntity, MemberEntity memberEntity) {
        proceedingQuestRepository.deleteByQuestEntityAndMemberEntity(questEntity, memberEntity);
    }

    public void deleteAllByMemberId(long memberId) {
        proceedingQuestRepository.deleteAllByMemberEntityId(memberId);
    }
}
