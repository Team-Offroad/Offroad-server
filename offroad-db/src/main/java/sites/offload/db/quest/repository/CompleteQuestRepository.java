package sites.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.member.entity.MemberEntity;
import sites.offload.db.quest.entity.CompleteQuestEntity;
import sites.offload.db.quest.entity.QuestEntity;

public interface CompleteQuestRepository extends JpaRepository<CompleteQuestEntity, Long> {
    void deleteByQuestEntityAndMemberEntity(QuestEntity questEntity, MemberEntity memberEntity);

    boolean existsByQuestEntityAndMemberEntity(QuestEntity questEntity, MemberEntity memberEntity);
}
