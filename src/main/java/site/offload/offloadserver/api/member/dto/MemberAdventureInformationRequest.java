package site.offload.offloadserver.api.member.dto;

public record MemberAdventureInformationRequest(Long memberId, String category, Integer characterId) {

    public static MemberAdventureInformationRequest of(final Long memberId, final String category, final Integer characterId) {
        return new MemberAdventureInformationRequest(memberId, category, characterId);
    }
}
