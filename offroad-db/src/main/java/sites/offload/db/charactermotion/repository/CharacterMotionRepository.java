package sites.offload.db.charactermotion.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.character.entity.Character;
import sites.offload.db.charactermotion.entity.CharacterMotion;
import sites.offload.enums.PlaceCategory;

import java.util.Optional;

public interface CharacterMotionRepository extends CrudRepository<CharacterMotion, Integer> {

    Optional<CharacterMotion> findCharacterMotionByPlaceCategoryAndCharacter(PlaceCategory placeCategory, Character character);
}
