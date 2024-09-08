package site.offload.api.emblem.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GainedEmblemListResponse(
        @Schema(description = "획득한 엠블럼 목록")
        List<GainedEmblemResponse> emblems
) {
    public static GainedEmblemListResponse of(List<GainedEmblemResponse> emblems) {
        return new GainedEmblemListResponse(emblems);
    }
}
