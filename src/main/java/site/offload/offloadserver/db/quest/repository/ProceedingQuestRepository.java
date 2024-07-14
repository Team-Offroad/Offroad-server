package site.offload.offloadserver.db.quest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.quest.entity.ProceedingQuest;
import site.offload.offloadserver.db.quest.entity.Quest;

import java.util.List;
import java.util.Optional;

public interface ProceedingQuestRepository extends JpaRepository<ProceedingQuest, Long> {

    List<ProceedingQuest> findAllByMemberIdOrderByUpdatedAtDesc(Long memberId);

    Optional<ProceedingQuest> findByMemberAndQuest(Member member, Quest quest);

    boolean existsByMemberAndQuest(Member member, Quest quest);
}
