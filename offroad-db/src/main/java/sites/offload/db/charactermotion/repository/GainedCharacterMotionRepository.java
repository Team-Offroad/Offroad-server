package sites.offload.db.charactermotion.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.charactermotion.entity.CharacterMotion;
import sites.offload.db.charactermotion.entity.GainedCharacterMotion;
import sites.offload.db.member.entity.Member;

public interface GainedCharacterMotionRepository extends CrudRepository<GainedCharacterMotion, Long> {
    boolean existsByCharacterMotionAndMember(CharacterMotion characterMotion, Member member);
}
