package site.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.entity.ProceedingQuestEntity;
import site.offload.db.quest.entity.QuestEntity;

import java.util.List;
import java.util.Optional;

public interface ProceedingQuestRepository extends JpaRepository<ProceedingQuestEntity, Long> {

    List<ProceedingQuestEntity> findAllByMemberEntityIdOrderByUpdatedAtDesc(Long memberId);

    Optional<ProceedingQuestEntity> findByMemberEntityAndQuestEntity(MemberEntity memberEntity, QuestEntity questEntity);

    boolean existsByMemberEntityAndQuestEntity(MemberEntity memberEntity, QuestEntity questEntity);

    void deleteByQuestEntityAndMemberEntity(QuestEntity questEntity, MemberEntity memberEntity);
}
