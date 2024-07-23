package sites.offload.db.character.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.character.entity.Character;
import sites.offload.db.character.entity.GainedCharacter;
import sites.offload.db.member.entity.Member;

public interface GainedCharacterRepository extends CrudRepository<GainedCharacter, Long> {

    boolean existsByCharacterAndMember(Character character, Member member);

}
