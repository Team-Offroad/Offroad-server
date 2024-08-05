package site.offload.api.emblem.dto.response;

import java.util.List;

public record EmblemsResponse(
        List<EmblemResponse> gainedEmblems,
        List<EmblemResponse> notGainedEmblems
) {
    public static EmblemsResponse of(List<EmblemResponse> gainedEmblems, List<EmblemResponse> notGainedEmblems){
        return new EmblemsResponse(gainedEmblems, notGainedEmblems);
    }
}
