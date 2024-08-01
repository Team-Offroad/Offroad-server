package site.offload.api.character;

import site.offload.db.character.entity.CharacterEntity;

public class CharacterEntityFixtureCreator {
    public static CharacterEntity createCharacterEntity(String name,
                                                        String characterCode,
                                                        String characterAdventureSuccessImageUrl,
                                                        String characterBaseImageUrl,
                                                        String characterSelectImageUrl,
                                                        String characterSpotLightImageUrl,
                                                        String characterAdventureQRFailureImageUrl,
                                                        String notGainedCharacterThumbnailImageUrl,
                                                        String description,
                                                        String characterAdventureLocationFailureImageUrl) {
        return CharacterEntity.builder()
                .name(name)
                .characterCode(characterCode)
                .characterAdventureSuccessImageUrl(characterAdventureSuccessImageUrl)
                .characterBaseImageUrl(characterBaseImageUrl)
                .characterSelectImageUrl(characterSelectImageUrl)
                .characterSpotLightImageUrl(characterSpotLightImageUrl)
                .characterAdventureQRFailureImageUrl(characterAdventureQRFailureImageUrl)
                .notGainedCharacterThumbnailImageUrl(notGainedCharacterThumbnailImageUrl)
                .description(description)
                .characterAdventureLocationFailureImageUrl(characterAdventureLocationFailureImageUrl)
                .build();
    }
}
