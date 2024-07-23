package site.offload.api.character.dto.response;

import lombok.Builder;

@Builder
public record CharacterResponse(Integer id, String description, String characterBaseImageUrl, String characterCode,
                                String name) {

    public static CharacterResponse of(Integer id, String description, String characterBaseImageUrl, String characterCode, String name) {
        return CharacterResponse.builder()
                .id(id)
                .description(description)
                .characterBaseImageUrl(characterBaseImageUrl)
                .name(name)
                .characterCode(characterCode)
                .build();

    }
}
