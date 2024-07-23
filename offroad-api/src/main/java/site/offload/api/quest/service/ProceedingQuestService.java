package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.api.exception.NotFoundException;
import sites.offload.db.member.entity.Member;
import sites.offload.db.quest.entity.ProceedingQuest;
import sites.offload.db.quest.entity.Quest;
import sites.offload.db.quest.repository.ProceedingQuestRepository;
import sites.offload.enums.ErrorMessage;

@Component
@RequiredArgsConstructor
public class ProceedingQuestService {

    private final ProceedingQuestRepository proceedingQuestRepository;

    public ProceedingQuest save(ProceedingQuest proceedingQuest) {
        return proceedingQuestRepository.save(proceedingQuest);
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

    public void addCurrentClearCount(ProceedingQuest proceedingQuest) {
        proceedingQuest.addCurrentClearCount();
    }

    public void updateCurrentClearCount(ProceedingQuest proceedingQuest, int count) {
        proceedingQuest.updateCurrentClearCount(count);
    }

    public void deleteProceedingQuest(Quest quest, Member member) {
        proceedingQuestRepository.deleteByQuestAndMember(quest, member);
    }
}
