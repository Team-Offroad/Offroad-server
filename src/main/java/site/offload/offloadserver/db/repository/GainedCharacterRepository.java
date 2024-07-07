package site.offload.offloadserver.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.offloadserver.db.entity.GainedCharacter;

public interface GainedCharacterRepository extends JpaRepository<GainedCharacter, Long> {
}
