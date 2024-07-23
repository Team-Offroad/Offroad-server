package sites.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.member.entity.Member;
import sites.offload.db.quest.entity.CompleteQuest;
import sites.offload.db.quest.entity.Quest;

public interface CompleteQuestRepository extends JpaRepository<CompleteQuest, Long> {
    void deleteByQuestAndMember(Quest quest, Member member);

    boolean existsByQuestAndMember(Quest quest, Member member);
}
