package sites.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.quest.entity.QuestRewardEntity;

import java.util.Optional;

public interface QuestRewardRepository extends JpaRepository<QuestRewardEntity, Integer> {
    Optional<QuestRewardEntity> findByQuestId(Integer questId);
}
