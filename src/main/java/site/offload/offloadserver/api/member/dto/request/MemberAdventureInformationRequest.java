package site.offload.offloadserver.api.member.dto.request;

public record MemberAdventureInformationRequest(Long memberId, String category) {

    public static MemberAdventureInformationRequest of(final Long memberId, final String category) {
        return new MemberAdventureInformationRequest(memberId, category);
    }
}
