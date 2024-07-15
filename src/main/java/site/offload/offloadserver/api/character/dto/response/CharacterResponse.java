package site.offload.offloadserver.api.character.dto.response;

import lombok.Builder;

@Builder
public record CharacterResponse(Integer id, String description, String characterBaseImageUrl, String characterCode) {

    public static CharacterResponse of(Integer id, String description, String characterBaseImageUrl, String characterCode) {
        return CharacterResponse.builder()
                .id(id)
                .description(description)
                .characterBaseImageUrl(characterBaseImageUrl)
                .characterCode(characterCode).build();

    }
}
