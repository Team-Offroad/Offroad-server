package site.offload.offloadserver.db.charactermotion.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.charactermotion.entity.CharacterMotion;
import site.offload.offloadserver.db.charactermotion.entity.GainedCharacterMotion;
import site.offload.offloadserver.db.member.entity.Member;

public interface GainedCharacterMotionRepository extends CrudRepository<GainedCharacterMotion, Long> {
    boolean existsByCharacterMotionAndMember(CharacterMotion characterMotion, Member member);
}
