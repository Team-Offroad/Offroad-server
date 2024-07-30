package site.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.entity.CompleteQuestEntity;
import site.offload.db.quest.entity.QuestEntity;

public interface CompleteQuestRepository extends JpaRepository<CompleteQuestEntity, Long> {
    void deleteAllByMemberEntityId(long memberId);

    boolean existsByQuestEntityAndMemberEntity(QuestEntity questEntity, MemberEntity memberEntity);
}
