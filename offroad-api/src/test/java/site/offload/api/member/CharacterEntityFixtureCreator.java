package site.offload.api.member;

import site.offload.db.character.entity.CharacterEntity;

public class CharacterEntityFixtureCreator {

    public static CharacterEntity createCharacterEntity(
            String name,
            String description,
            String characterBaseImageUrl,
            String characterSpotLightImageUrl,
            String characterAdventureSuccessImageUrl,
            String characterAdventureQRFailureImageUrl,
            String characterAdventureLocationFailureImageUrl,
            String characterSelectImageUrl,
            String characterCode,
            String notGainedCharacterThumbnailImageUrl) {
        return CharacterEntity.builder()
                .name(name)
                .description(description)
                .characterBaseImageUrl(characterBaseImageUrl)
                .characterSpotLightImageUrl(characterSpotLightImageUrl)
                .characterAdventureSuccessImageUrl(characterAdventureSuccessImageUrl)
                .characterAdventureQRFailureImageUrl(characterAdventureQRFailureImageUrl)
                .characterAdventureLocationFailureImageUrl(characterAdventureLocationFailureImageUrl)
                .characterSelectImageUrl(characterSelectImageUrl)
                .characterCode(characterCode)
                .notGainedCharacterThumbnailImageUrl(notGainedCharacterThumbnailImageUrl)
                .build();
    }
}
