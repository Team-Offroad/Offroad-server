package site.offload.offloadserver.db.quest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import site.offload.offloadserver.db.quest.entity.QuestReward;

import java.util.Optional;

public interface QuestRewardRepository extends JpaRepository<QuestReward, Integer> {
    Optional<QuestReward> findByQuestId(Integer questId);
}
