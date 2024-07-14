package site.offload.offloadserver.api.member.dto.request;

public record GoogleSocialLoginRequest(
        SocialPlatform socialPlatform,
        String code
) {
    public static GoogleSocialLoginRequest of(SocialPlatform socialPlatform, String code) {
        return new GoogleSocialLoginRequest(socialPlatform, code);
    }
}
