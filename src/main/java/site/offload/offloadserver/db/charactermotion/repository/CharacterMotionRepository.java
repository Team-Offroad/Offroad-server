package site.offload.offloadserver.db.charactermotion.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.character.entity.Character;
import site.offload.offloadserver.db.charactermotion.entity.CharacterMotion;
import site.offload.offloadserver.db.place.entity.PlaceCategory;

import java.util.Optional;

public interface CharacterMotionRepository extends CrudRepository<CharacterMotion, Integer> {

    Optional<CharacterMotion> findCharacterMotionByPlaceCategoryAndCharacter(PlaceCategory placeCategory, Character character);
}
