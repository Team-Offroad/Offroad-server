package site.offload.offloadserver.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.quest.entity.ProceedingQuest;
import site.offload.offloadserver.db.quest.entity.Quest;
import site.offload.offloadserver.db.quest.repository.ProceedingQuestRepository;

@Component
@RequiredArgsConstructor
public class ProceedingQuestService {

    private final ProceedingQuestRepository proceedingQuestRepository;

    public Long save(ProceedingQuest proceedingQuest) {
        return proceedingQuestRepository.save(proceedingQuest).getId();
    }

    public boolean existsByMemberAndQuest(Member member, Quest quest) {
        return proceedingQuestRepository.existsByMemberAndQuest(member, quest);
    }

    public ProceedingQuest findById(Long id) {
        return proceedingQuestRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PROCEEDING_QUEST_NOTFOUND_EXCEPTION)
        );
    }

    public ProceedingQuest findByMemberAndQuest(Member member, Quest quest) {
        return proceedingQuestRepository.findByMemberAndQuest(member, quest).orElseThrow(
                () -> new NotFoundException(ErrorMessage.PROCEEDING_QUEST_NOTFOUND_EXCEPTION)
        );
    }
}
