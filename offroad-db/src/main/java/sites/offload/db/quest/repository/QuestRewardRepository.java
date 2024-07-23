package sites.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.quest.entity.QuestReward;

import java.util.Optional;

public interface QuestRewardRepository extends JpaRepository<QuestReward, Integer> {
    Optional<QuestReward> findByQuestId(Integer questId);
}
