package site.offload.offloadserver.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.db.quest.entity.QuestReward;
import site.offload.offloadserver.db.quest.repository.QuestRewardRepository;

@Service
@RequiredArgsConstructor
public class QuestRewardService {

    private final QuestRewardRepository questRewardRepository;

    public QuestReward findByQuestId(Integer questId) {
        return questRewardRepository.findByQuestId(questId);
    }
}
