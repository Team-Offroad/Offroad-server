package site.offload.offloadserver.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.offloadserver.db.entity.Character;

public interface CharacterRepository extends JpaRepository<Character, Integer> {
}
