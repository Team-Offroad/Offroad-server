package site.offload.offloadserver.db.quest.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.quest.entity.QuestReward;

public interface QuestRewardRepository extends CrudRepository<QuestReward, Integer> {
}
