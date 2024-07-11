package site.offload.offloadserver.api.emblem.dto.response;

import java.util.List;

public record GainedEmblemListResponse(List<GainedEmblemResponse> emblems) {
    public static GainedEmblemListResponse of(List<GainedEmblemResponse> emblems) {
        return new GainedEmblemListResponse(emblems);
    }
}
