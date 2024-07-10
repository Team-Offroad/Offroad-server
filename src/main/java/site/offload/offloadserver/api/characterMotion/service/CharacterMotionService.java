package site.offload.offloadserver.api.characterMotion.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.db.character.entity.Character;
import site.offload.offloadserver.db.charactermotion.entity.CharacterMotion;
import site.offload.offloadserver.db.charactermotion.repository.CharacterMotionRepository;
import site.offload.offloadserver.db.place.entity.PlaceCategory;

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
