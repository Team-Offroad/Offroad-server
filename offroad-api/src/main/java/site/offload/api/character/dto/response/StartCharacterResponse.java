package site.offload.api.character.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record StartCharacterResponse(
        @Schema(description = "캐릭터 ID", example = "1")
        Integer id,
        @Schema(description = "캐릭터 설명", example = "테스트 캐릭터")
        String description,
        @Schema(description = "캐릭터 이미지 URL", example = "https://test.com/test.jpg")
        String characterBaseImageUrl,
        @Schema(description = "캐릭터 코드", example = "CHAR001")
        String characterCode,
        @Schema(description = "캐릭터 이름", example = "테스트 캐릭터")
        String name
) {

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
