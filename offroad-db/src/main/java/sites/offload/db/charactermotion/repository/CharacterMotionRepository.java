package sites.offload.db.charactermotion.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.character.entity.CharacterEntity;
import sites.offload.db.charactermotion.entity.CharacterMotionEntity;
import sites.offload.enums.PlaceCategory;

import java.util.Optional;

public interface CharacterMotionRepository extends CrudRepository<CharacterMotionEntity, Integer> {

    Optional<CharacterMotionEntity> findCharacterMotionByPlaceCategoryAndCharacter(PlaceCategory placeCategory, CharacterEntity characterEntity);
}
