package site.offload.api.charactermotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.api.exception.NotFoundException;
import sites.offload.db.character.entity.Character;
import sites.offload.db.charactermotion.entity.CharacterMotion;
import sites.offload.db.charactermotion.repository.CharacterMotionRepository;
import sites.offload.enums.ErrorMessage;
import sites.offload.enums.PlaceCategory;

@Component
@RequiredArgsConstructor
public class CharacterMotionService {

    private final CharacterMotionRepository characterMotionRepository;

    public CharacterMotion findByCharacterAndPlaceCategory(Character character, PlaceCategory placeCategory) {
        return characterMotionRepository.findCharacterMotionByPlaceCategoryAndCharacter(placeCategory, character).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CHARACTER_MOTION_NOTFOUND_EXCEPTION)
        );
    }

}
