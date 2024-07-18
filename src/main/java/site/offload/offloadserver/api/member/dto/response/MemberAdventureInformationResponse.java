package site.offload.offloadserver.api.member.dto.response;

import lombok.Builder;

@Builder
public record MemberAdventureInformationResponse(String nickname, String emblemName, String baseImageUrl,
                                                 String motionImageUrl,
                                                 String characterName) {

    public static MemberAdventureInformationResponse of(final String nickname, final String emblemName,
     final String baseImageUrl, final String motionImageUrl, final String characterName) {
        return MemberAdventureInformationResponse.builder()
                .nickname(nickname)
                .emblemName(emblemName)
                .characterName(characterName)
                .baseImageUrl(baseImageUrl)
                .motionImageUrl(motionImageUrl)
                .build();
    }
}
