package site.offload.offloadserver.db.quest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.offloadserver.db.quest.entity.ProceedingQuest;
import site.offload.offloadserver.db.quest.entity.Quest;

import java.util.List;

public interface ProceedingQuestRepository extends JpaRepository<ProceedingQuest, Long> {

    List<ProceedingQuest> findAllByMemberIdOrderByUpdatedAt(Long memberId);
}
