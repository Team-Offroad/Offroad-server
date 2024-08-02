package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.exception.NotFoundException;
import site.offload.db.quest.entity.QuestRewardEntity;
import site.offload.db.quest.repository.QuestRewardRepository;
import site.offload.enums.response.ErrorMessage;

@Service
@RequiredArgsConstructor
public class QuestRewardService {

    private final QuestRewardRepository questRewardRepository;

    public QuestRewardEntity findByQuestId(Integer questId) {
        return questRewardRepository.findByQuestId(questId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.ERROR_MESSAGE));
    }

    public boolean isExistByQuestId(Integer questId) {
        return questRewardRepository.existsByQuestId(questId);
    }
}
