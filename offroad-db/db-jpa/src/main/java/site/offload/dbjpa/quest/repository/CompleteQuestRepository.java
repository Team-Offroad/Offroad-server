package site.offload.dbjpa.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.dbjpa.member.entity.MemberEntity;
import site.offload.dbjpa.quest.entity.CompleteQuestEntity;
import site.offload.dbjpa.quest.entity.QuestEntity;

public interface CompleteQuestRepository extends JpaRepository<CompleteQuestEntity, Long> {
    void deleteAllByMemberEntityId(long memberId);

    boolean existsByQuestEntityAndMemberEntity(QuestEntity questEntity, MemberEntity memberEntity);
}
