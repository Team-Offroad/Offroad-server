package site.offload.offloadserver.db.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.entity.GainedCharacter;

public interface GainedCharacterRepository extends CrudRepository<GainedCharacter, Long> {
}
