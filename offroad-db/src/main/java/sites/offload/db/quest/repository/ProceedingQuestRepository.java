package sites.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.member.entity.MemberEntity;
import sites.offload.db.quest.entity.ProceedingQuestEntity;
import sites.offload.db.quest.entity.QuestEntity;

import java.util.List;
import java.util.Optional;

public interface ProceedingQuestRepository extends JpaRepository<ProceedingQuestEntity, Long> {

    List<ProceedingQuestEntity> findAllByMemberIdOrderByUpdatedAtDesc(Long memberId);

    Optional<ProceedingQuestEntity> findByMemberAndQuest(MemberEntity memberEntity, QuestEntity questEntity);

    boolean existsByMemberAndQuest(MemberEntity memberEntity, QuestEntity questEntity);

    void deleteByQuestAndMember(QuestEntity questEntity, MemberEntity memberEntity);
}
