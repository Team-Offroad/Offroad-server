package site.offload.api.emblem.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record EmblemsResponse(
        @Schema(description = "획득한 칭호 목록")
        List<EmblemResponse> gainedEmblems,
        @Schema(description = "획득하지 못한 칭호 목록")
        List<EmblemResponse> notGainedEmblems
) {
    public static EmblemsResponse of(List<EmblemResponse> gainedEmblems, List<EmblemResponse> notGainedEmblems){
        return new EmblemsResponse(gainedEmblems, notGainedEmblems);
    }
}
