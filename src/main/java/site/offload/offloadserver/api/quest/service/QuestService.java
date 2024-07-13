package site.offload.offloadserver.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.db.quest.entity.ProceedingQuest;
import site.offload.offloadserver.db.quest.repository.ProceedingQuestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final ProceedingQuestRepository proceedingQuestRepository;

    public List<ProceedingQuest> findProceedingQuests(Long memberId) {
        return proceedingQuestRepository.findAllByMemberIdOrderByUpdatedAt(memberId);
    }

}
