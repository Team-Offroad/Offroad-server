package site.offload.offloadserver.api.emblem.dto.request;

public record UpdateCurrentEmblemRequest(String emblemCode, Long memberId) {

    public static UpdateCurrentEmblemRequest of(String emblemCode, Long memberId) {
        return new UpdateCurrentEmblemRequest(emblemCode, memberId);
    }
}
