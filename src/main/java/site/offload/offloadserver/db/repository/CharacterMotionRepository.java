package site.offload.offloadserver.db.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.entity.CharacterMotion;

public interface CharacterMotionRepository extends CrudRepository<CharacterMotion, Integer> {
}
