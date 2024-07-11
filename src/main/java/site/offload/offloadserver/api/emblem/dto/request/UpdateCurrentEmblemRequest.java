package site.offload.offloadserver.api.emblem.dto.request;

public record UpdateCurrentEmblemRequest(String emblemName, Long memberId) {

    public static UpdateCurrentEmblemRequest of(String emblemName, Long memberId) {
        return new UpdateCurrentEmblemRequest(emblemName, memberId);
    }
}
