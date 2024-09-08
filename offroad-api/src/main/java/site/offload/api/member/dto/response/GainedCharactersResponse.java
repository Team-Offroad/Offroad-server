package site.offload.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "획득한 캐릭터 응답 목록")
public record GainedCharactersResponse(
        @Schema(description = "획득한 캐릭터 목록")
        List<GainedCharacterResponse> gainedCharacters,
        @Schema(description = "미획득한 캐릭터 목록")
        List<GainedCharacterResponse> notGainedCharacters,
        @Schema(description = "대표 캐릭터 ID", example = "1")
        Integer representativeCharacterId
) {
    public static GainedCharactersResponse of(List<GainedCharacterResponse> gainedCharacters, List<GainedCharacterResponse> notGainedCharacters, Integer representativeCharacterId) {
        return new GainedCharactersResponse(gainedCharacters, notGainedCharacters, representativeCharacterId);
    }
}
