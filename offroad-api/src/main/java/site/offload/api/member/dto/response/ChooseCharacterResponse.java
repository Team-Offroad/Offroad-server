package site.offload.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChooseCharacterResponse(
        @Schema(description = "캐릭터 이미지 URL", example = "https://test.com/test.jpg")
        String characterImageUrl
) {
    public static ChooseCharacterResponse of(String characterImageUrl) {
        return new ChooseCharacterResponse(characterImageUrl);
    }
}