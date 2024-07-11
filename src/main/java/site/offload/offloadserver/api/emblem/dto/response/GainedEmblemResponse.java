package site.offload.offloadserver.api.emblem.dto.response;


public record GainedEmblemResponse(String emblemCode, String emblemName) {

    public static GainedEmblemResponse of(String emblemCode, String emblemName) {
        return new GainedEmblemResponse(emblemCode, emblemName);
    }
}
