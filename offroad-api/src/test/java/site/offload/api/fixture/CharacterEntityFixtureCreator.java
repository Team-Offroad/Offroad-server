package site.offload.api.fixture;

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
                                                        String characterAdventureLocationFailureImageUrl,
                                                        String characterMainColorCode,
                                                        String characterSubColorCode,
                                                        String summaryDescription,
                                                        String characterIconImageUrl) {
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
                .characterMainColorCode(characterMainColorCode)
                .characterSubColorCode(characterSubColorCode)
                .characterIconImageUrl(characterIconImageUrl)
                .summaryDescription(summaryDescription)
                .build();
    }
}
