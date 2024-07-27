package sites.offload.db.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.character.entity.CharacterEntity;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Integer> {
    Optional<CharacterEntity> findByName(String name);
}
