package site.offload.dbjpa.charactermotion.repository;


import org.springframework.data.repository.CrudRepository;
import site.offload.dbjpa.charactermotion.entity.CharacterMotionEntity;
import site.offload.dbjpa.charactermotion.entity.GainedCharacterMotionEntity;
import site.offload.dbjpa.member.entity.MemberEntity;

public interface GainedCharacterMotionRepository extends CrudRepository<GainedCharacterMotionEntity, Long> {
    boolean existsByCharacterMotionEntityAndMemberEntity(CharacterMotionEntity characterMotionEntity, MemberEntity memberEntity);

    void deleteAllByMemberEntityId(long memberId);
}
