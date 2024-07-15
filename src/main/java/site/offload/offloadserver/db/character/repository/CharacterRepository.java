package site.offload.offloadserver.db.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.character.entity.Character;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
}
