package site.offload.offloadserver.db.character.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.character.entity.GainedCharacter;

public interface GainedCharacterRepository extends CrudRepository<GainedCharacter, Long> {
}
