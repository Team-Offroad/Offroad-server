package sites.offload.db.charactermotion.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.charactermotion.entity.CharacterMotionEntity;
import sites.offload.db.charactermotion.entity.GainedCharacterMotionEntity;
import sites.offload.db.member.entity.MemberEntity;

public interface GainedCharacterMotionRepository extends CrudRepository<GainedCharacterMotionEntity, Long> {
    boolean existsByCharacterMotionAndMember(CharacterMotionEntity characterMotionEntity, MemberEntity memberEntity);
}
