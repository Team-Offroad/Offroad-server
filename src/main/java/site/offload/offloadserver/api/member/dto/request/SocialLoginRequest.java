package site.offload.offloadserver.api.member.dto.request;

public record SocialLoginRequest(
        SocialPlatform socialPlatform,
        String name,
        String code
) {
    public static SocialLoginRequest of(SocialPlatform socialPlatform, String name, String code) {
        return new SocialLoginRequest(socialPlatform, name, code);
    }
}
