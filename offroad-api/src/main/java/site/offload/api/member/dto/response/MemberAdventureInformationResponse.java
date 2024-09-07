package site.offload.api.member.dto.response;

import lombok.Builder;

@Builder
public record MemberAdventureInformationResponse(String nickname, String emblemName, String baseImageUrl,
                                                 String motionImageUrl,
                                                 String characterName,
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
