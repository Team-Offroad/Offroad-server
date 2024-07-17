package site.offload.offloadserver.api.member.dto.response;

public record VerifyPositionDistanceResponse(boolean isValidPosition, String successCharacterImageUrl) {

    public static VerifyPositionDistanceResponse of(boolean isValidPosition, String characterImageUrl) {
        return new VerifyPositionDistanceResponse(isValidPosition, characterImageUrl);
    }
}
