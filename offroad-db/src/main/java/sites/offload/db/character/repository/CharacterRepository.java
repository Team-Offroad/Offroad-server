package sites.offload.db.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.character.entity.Character;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
    Optional<Character> findByName(String name);
}
