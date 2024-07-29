package site.offload.api.charactermotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.api.exception.NotFoundException;
import sites.offload.db.character.entity.CharacterEntity;
import sites.offload.db.charactermotion.entity.CharacterMotionEntity;
import sites.offload.db.charactermotion.repository.CharacterMotionRepository;
import sites.offload.enums.ErrorMessage;
import sites.offload.enums.PlaceCategory;

@Component
@RequiredArgsConstructor
public class CharacterMotionService {

    private final CharacterMotionRepository characterMotionRepository;

    public CharacterMotionEntity findByCharacterAndPlaceCategory(CharacterEntity characterEntity, PlaceCategory placeCategory) {
        return characterMotionRepository.findCharacterMotionByPlaceCategoryAndCharacterEntity(placeCategory, characterEntity).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CHARACTER_MOTION_NOTFOUND_EXCEPTION)
        );
    }

}
