package site.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.offload.db.quest.entity.QuestRewardEntity;

import java.util.List;
import java.util.Optional;

public interface QuestRewardRepository extends JpaRepository<QuestRewardEntity, Integer> {
    Optional<QuestRewardEntity> findByQuestId(Integer questId);

    boolean existsByQuestId(Integer questId);

    //@Query("SELECT q FROM QuestRewardEntity q WHERE q.rewardList.emblemCode != null")
    @Query("SELECT q FROM QuestRewardEntity q WHERE q.rewardList.emblemCode IS NOT NULL")
    List<QuestRewardEntity> findAllWithEmblems();
}
