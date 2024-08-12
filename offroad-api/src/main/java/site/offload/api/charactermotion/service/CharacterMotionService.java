package site.offload.api.charactermotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.api.exception.NotFoundException;
import site.offload.dbjpa.character.entity.CharacterEntity;
import site.offload.dbjpa.charactermotion.entity.CharacterMotionEntity;
import site.offload.dbjpa.charactermotion.repository.CharacterMotionRepository;
import site.offload.enums.response.ErrorMessage;
import site.offload.enums.place.PlaceCategory;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterMotionService {

    private final CharacterMotionRepository characterMotionRepository;

    public CharacterMotionEntity findByCharacterAndPlaceCategory(CharacterEntity characterEntity, PlaceCategory placeCategory) {
        return characterMotionRepository.findCharacterMotionByPlaceCategoryAndCharacterEntity(placeCategory, characterEntity).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CHARACTER_MOTION_NOTFOUND_EXCEPTION)
        );
    }

    public List<CharacterMotionEntity> findCharacterMotionsByCharacterEntity(CharacterEntity characterEntity) {
        return characterMotionRepository.findAllByCharacterEntity(characterEntity);
    }

}
