package site.offload.offloadserver.api.member.dto;

public record MemberAdventureInformationResponse(String nickname, String emblemName, String characterImgUrl,
                                                 String characterName) {

    public static MemberAdventureInformationResponse of(final String nickname, final String emblemName, final String characterImgUrl, final String characterName) {
        return new MemberAdventureInformationResponse(nickname, emblemName, characterImgUrl, characterName);
    }
}
