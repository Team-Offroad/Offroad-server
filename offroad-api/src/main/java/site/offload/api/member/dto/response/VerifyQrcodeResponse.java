package site.offload.api.member.dto.response;

public record VerifyQrcodeResponse(boolean isQRMatched, String characterImageUrl) {

    public static VerifyQrcodeResponse of(boolean isQRMatched, String characterImageUrl) {
        return new VerifyQrcodeResponse(isQRMatched, characterImageUrl);
    }
}
