package site.offload.dbjpa.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.dbjpa.member.entity.MemberEntity;
import site.offload.dbjpa.quest.entity.ProceedingQuestEntity;
import site.offload.dbjpa.quest.entity.QuestEntity;

import java.util.List;
import java.util.Optional;

public interface ProceedingQuestRepository extends JpaRepository<ProceedingQuestEntity, Long> {

    List<ProceedingQuestEntity> findAllByMemberEntityIdOrderByUpdatedAtDesc(Long memberId);

    Optional<ProceedingQuestEntity> findByMemberEntityAndQuestEntity(MemberEntity memberEntity, QuestEntity questEntity);

    boolean existsByMemberEntityAndQuestEntity(MemberEntity memberEntity, QuestEntity questEntity);

    void deleteByQuestEntityAndMemberEntity(QuestEntity questEntity, MemberEntity memberEntity);

    void deleteAllByMemberEntityId(long memberId);
}
