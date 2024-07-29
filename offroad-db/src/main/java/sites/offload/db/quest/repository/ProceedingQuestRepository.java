package sites.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.member.entity.MemberEntity;
import sites.offload.db.quest.entity.ProceedingQuestEntity;
import sites.offload.db.quest.entity.QuestEntity;

import java.util.List;
import java.util.Optional;

public interface ProceedingQuestRepository extends JpaRepository<ProceedingQuestEntity, Long> {

    List<ProceedingQuestEntity> findAllByMemberEntityIdOrderByUpdatedAtDesc(Long memberId);

    Optional<ProceedingQuestEntity> findByMemberEntityAndQuestEntity(MemberEntity memberEntity, QuestEntity questEntity);

    boolean existsByMemberEntityAndQuestEntity(MemberEntity memberEntity, QuestEntity questEntity);

    void deleteByQuestEntityAndMemberEntity(QuestEntity questEntity, MemberEntity memberEntity);
}
