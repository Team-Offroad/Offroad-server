package site.offload.offloadserver.db.quest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.quest.entity.CompleteQuest;
import site.offload.offloadserver.db.quest.entity.Quest;
import site.offload.offloadserver.db.quest.entity.QuestReward;

public interface CompleteQuestRepository extends JpaRepository<CompleteQuest, Long> {
    void deleteByQuestAndMember(Quest quest, Member member);
}
