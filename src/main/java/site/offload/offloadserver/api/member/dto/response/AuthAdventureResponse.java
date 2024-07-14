package site.offload.offloadserver.api.member.dto.response;

public record AuthAdventureResponse(boolean isQRMatched) {

    public static AuthAdventureResponse of(boolean isQRMatched) {
        return new AuthAdventureResponse(isQRMatched);
    }
}
