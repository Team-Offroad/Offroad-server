package site.offload.api.member.dto.response;

public record NicknameCheckResponse(
        boolean isDuplicate
) {
    public static NicknameCheckResponse of(boolean isDuplicate) {
        return new NicknameCheckResponse(isDuplicate);
    }
}
