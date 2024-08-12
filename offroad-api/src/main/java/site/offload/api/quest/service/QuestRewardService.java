package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.exception.NotFoundException;
import site.offload.dbjpa.quest.entity.QuestEntity;
import site.offload.dbjpa.quest.entity.QuestRewardEntity;
import site.offload.dbjpa.quest.repository.QuestRewardRepository;
import site.offload.enums.response.ErrorMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestRewardService {

    private final QuestRewardRepository questRewardRepository;

    public QuestRewardEntity findByQuestId(Integer questId) {
        return questRewardRepository.findByQuestId(questId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.QUEST_REWARD_NOTFOUND_EXCEPTION));
    }

    public List<QuestRewardEntity> findAllByQuestId(int questId) {
        return questRewardRepository.findAllByQuestId(questId);
    }
    public boolean isExistByQuestId(Integer questId) {
        return questRewardRepository.existsByQuestId(questId);
    }

    public List<QuestRewardEntity> findQuestWithEmblems() {
        return questRewardRepository.findAllWithEmblems();
    }

    public QuestRewardEntity findByCouponCode(String couponCode) {
        return questRewardRepository.findByCouponCode(couponCode).orElseThrow(() -> new NotFoundException(ErrorMessage.QUEST_REWARD_NOTFOUND_EXCEPTION));
    }
}
