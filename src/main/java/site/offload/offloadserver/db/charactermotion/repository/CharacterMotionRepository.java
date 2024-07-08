package site.offload.offloadserver.db.charactermotion.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.charactermotion.entity.CharacterMotion;

public interface CharacterMotionRepository extends CrudRepository<CharacterMotion, Integer> {
}
