package sites.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.member.entity.Member;
import sites.offload.db.quest.entity.ProceedingQuest;
import sites.offload.db.quest.entity.Quest;

import java.util.List;
import java.util.Optional;

public interface ProceedingQuestRepository extends JpaRepository<ProceedingQuest, Long> {

    List<ProceedingQuest> findAllByMemberIdOrderByUpdatedAtDesc(Long memberId);

    Optional<ProceedingQuest> findByMemberAndQuest(Member member, Quest quest);

    boolean existsByMemberAndQuest(Member member, Quest quest);

    void deleteByQuestAndMember(Quest quest, Member member);
}
