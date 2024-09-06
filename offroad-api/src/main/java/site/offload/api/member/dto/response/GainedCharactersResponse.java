package site.offload.api.member.dto.response;

import java.util.List;

public record GainedCharactersResponse(
        List<GainedCharacterResponse> gainedCharacters,
        List<GainedCharacterResponse> notGainedCharacters,
        Integer representativeCharacterId
) {
    public static GainedCharactersResponse of(List<GainedCharacterResponse> gainedCharacters, List<GainedCharacterResponse> notGainedCharacters, Integer representativeCharacterId) {
        return new GainedCharactersResponse(gainedCharacters, notGainedCharacters, representativeCharacterId);
    }
}
