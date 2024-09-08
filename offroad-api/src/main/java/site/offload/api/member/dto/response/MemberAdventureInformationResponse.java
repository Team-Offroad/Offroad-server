package site.offload.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MemberAdventureInformationResponse(
        @Schema(description = "닉네임", example = "테스트 닉네임")
        String nickname,
        @Schema(description = "칭호 이름", example = "테스트 칭호")
        String emblemName,
        @Schema(description = "기본 이미지 URL", example = "https://test.com/test.jpg")
        String baseImageUrl,
        @Schema(description = "모션 이미지 URL", example = "https://test.com/test.jpg")
        String motionImageUrl,
        @Schema(description = "캐릭터 이름", example = "테스트 캐릭터")
        String characterName,
        @Schema(description = "모션 캡쳐 이미지 URL", example = "https://test.com/test.jpg")
        String motionCaptureImageUrl) {

    public static MemberAdventureInformationResponse of(final String nickname, final String emblemName,
                                                        final String baseImageUrl, final String motionImageUrl, final String characterName, final String motionCaptureImageUrl) {
        return MemberAdventureInformationResponse.builder()
                .nickname(nickname)
                .emblemName(emblemName)
                .characterName(characterName)
                .baseImageUrl(baseImageUrl)
                .motionImageUrl(motionImageUrl)
                .motionCaptureImageUrl(motionCaptureImageUrl)
                .build();
    }
}
