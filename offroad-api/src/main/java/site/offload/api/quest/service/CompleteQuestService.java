package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sites.offload.db.member.entity.Member;
import sites.offload.db.quest.entity.CompleteQuest;
import sites.offload.db.quest.entity.Quest;
import sites.offload.db.quest.repository.CompleteQuestRepository;

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
