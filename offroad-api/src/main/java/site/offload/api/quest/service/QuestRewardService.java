package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.exception.NotFoundException;
import sites.offload.db.quest.entity.QuestReward;
import sites.offload.db.quest.repository.QuestRewardRepository;
import sites.offload.enums.ErrorMessage;

@Service
@RequiredArgsConstructor
public class QuestRewardService {

    private final QuestRewardRepository questRewardRepository;

    public QuestReward findByQuestId(Integer questId) {
        return questRewardRepository.findByQuestId(questId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ERROR_MESSAGE));
    }
}
