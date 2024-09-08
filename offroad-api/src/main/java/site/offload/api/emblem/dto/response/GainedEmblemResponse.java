package site.offload.api.emblem.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;

public record GainedEmblemResponse(
        @Schema(description = "칭호 코드", example = "EMB001")
        String emblemCode,
        @Schema(description = "칭호 이름", example = "테스트 칭호")
        String emblemName
) {

    public static GainedEmblemResponse of(String emblemCode, String emblemName) {
        return new GainedEmblemResponse(emblemCode, emblemName);
    }
}
