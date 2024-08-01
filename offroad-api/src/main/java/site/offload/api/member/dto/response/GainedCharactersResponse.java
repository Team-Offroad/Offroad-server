package site.offload.api.member.dto.response;

import java.util.List;

public record GainedCharactersResponse(
        List<GainedCharacterResponse> isGainedCharacters,
        List<GainedCharacterResponse> isNotGainedCharacters
) {
    public static GainedCharactersResponse of(List<GainedCharacterResponse> isGainedCharacters, List<GainedCharacterResponse> isNotGainedCharacters) {
        return new GainedCharactersResponse(isGainedCharacters, isNotGainedCharacters);
    }
}
