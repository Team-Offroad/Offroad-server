package site.offload.api.character.dto.response;

import lombok.Builder;

@Builder
public record StartCharacterResponse(Integer id, String description, String characterBaseImageUrl, String characterCode,
                                     String name) {

    public static StartCharacterResponse of(Integer id, String description, String characterBaseImageUrl, String characterCode, String name) {
        return StartCharacterResponse.builder()
                .id(id)
                .description(description)
                .characterBaseImageUrl(characterBaseImageUrl)
                .name(name)
                .characterCode(characterCode)
                .build();

    }
}
