package site.offload.api.fixture;

import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.charactermotion.entity.CharacterMotionEntity;
import site.offload.enums.place.PlaceCategory;

public class CharacterMotionEntityFixtureCreator {

    public static CharacterMotionEntity createCharacterMotionEntity(CharacterEntity characterEntity, PlaceCategory placeCategory, String motionImageUrl, String notGainedMotionThumbnailImageUrl, String motionCaptureImageUrl) {
        return CharacterMotionEntity.builder()
                .characterEntity(characterEntity)
                .placeCategory(placeCategory)
                .motionImageUrl(motionImageUrl)
                .notGainedMotionThumbnailImageUrl(notGainedMotionThumbnailImageUrl)
                .motionCaptureImageUrl(motionCaptureImageUrl)
                .build();
    }
}
