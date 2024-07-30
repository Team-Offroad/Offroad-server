package site.offload.db.charactermotion.repository;


import org.springframework.data.repository.CrudRepository;
import site.offload.db.charactermotion.entity.CharacterMotionEntity;
import site.offload.db.charactermotion.entity.GainedCharacterMotionEntity;
import site.offload.db.member.entity.MemberEntity;

public interface GainedCharacterMotionRepository extends CrudRepository<GainedCharacterMotionEntity, Long> {
    boolean existsByCharacterMotionEntityAndMemberEntity(CharacterMotionEntity characterMotionEntity, MemberEntity memberEntity);

    void deleteAllByMemberEntityId(long memberId);
}
