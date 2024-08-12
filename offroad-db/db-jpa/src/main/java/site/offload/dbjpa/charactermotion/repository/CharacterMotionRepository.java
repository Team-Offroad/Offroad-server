package site.offload.dbjpa.charactermotion.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.dbjpa.character.entity.CharacterEntity;
import site.offload.dbjpa.charactermotion.entity.CharacterMotionEntity;
import site.offload.enums.place.PlaceCategory;

import java.util.List;
import java.util.Optional;

public interface CharacterMotionRepository extends JpaRepository<CharacterMotionEntity, Integer> {

    Optional<CharacterMotionEntity> findCharacterMotionByPlaceCategoryAndCharacterEntity(PlaceCategory placeCategory, CharacterEntity characterEntity);

    List<CharacterMotionEntity> findAllByCharacterEntity(CharacterEntity characterEntity);
}
