package site.offload.offloadserver.db.character.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.character.entity.Character;

public interface CharacterRepository extends CrudRepository<Character, Integer> {
}
