package site.offload.offloadserver.db.quest.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.quest.entity.Quest;

public interface QuestRepository extends CrudRepository<Quest, Integer> {
}
