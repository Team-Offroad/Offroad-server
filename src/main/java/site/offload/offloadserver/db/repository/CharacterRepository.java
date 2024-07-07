package site.offload.offloadserver.db.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.entity.Character;

public interface CharacterRepository extends CrudRepository<Character, Integer> {
}
