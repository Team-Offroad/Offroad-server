package site.offload.offloadserver.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.quest.entity.CompleteQuest;
import site.offload.offloadserver.db.quest.entity.Quest;
import site.offload.offloadserver.db.quest.repository.CompleteQuestRepository;

@Service
@RequiredArgsConstructor
public class CompleteQuestService {

    private final CompleteQuestRepository completeQuestRepository;

    public void saveCompleteQuest(Quest quest, Member member) {
        completeQuestRepository.save(
                CompleteQuest.builder()
                .quest(quest)
                .member(member)
                .build());
    }

    public boolean isExsistsByQuestAndMember(Quest quest, Member member) {
        return completeQuestRepository.existsByQuestAndMember(quest, member);
    }
}
