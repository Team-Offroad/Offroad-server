package site.offload.offloadserver.api.member.dto.request;

public record SocialLoginRequest(
        SocialPlatform socialPlatform,
        String code
) {
    public static SocialLoginRequest of(SocialPlatform socialPlatform, String code) {
        return new SocialLoginRequest(socialPlatform, code);
    }
}
