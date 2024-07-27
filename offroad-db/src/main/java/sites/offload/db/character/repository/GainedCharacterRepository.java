package sites.offload.db.character.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.character.entity.CharacterEntity;
import sites.offload.db.character.entity.GainedCharacterEntity;
import sites.offload.db.member.entity.MemberEntity;

public interface GainedCharacterRepository extends CrudRepository<GainedCharacterEntity, Long> {

    boolean existsByCharacterAndMember(CharacterEntity characterEntity, MemberEntity memberEntity);

}
