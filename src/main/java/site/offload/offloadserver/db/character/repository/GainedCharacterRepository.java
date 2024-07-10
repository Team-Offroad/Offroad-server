package site.offload.offloadserver.db.character.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.character.entity.GainedCharacter;
import site.offload.offloadserver.db.charactermotion.entity.CharacterMotion;
import site.offload.offloadserver.db.member.entity.Member;

public interface GainedCharacterRepository extends CrudRepository<GainedCharacter, Long> {

    boolean existsByCharacterAndMember(CharacterMotion character, Member member);
}
