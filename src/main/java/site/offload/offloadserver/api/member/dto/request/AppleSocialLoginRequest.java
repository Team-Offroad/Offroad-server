package site.offload.offloadserver.api.member.dto.request;

public record AppleSocialLoginRequest(
        SocialPlatform socialPlatform,
        String name,
        String code
) {
    public static AppleSocialLoginRequest of(SocialPlatform socialPlatform, String name, String code) {
        return new AppleSocialLoginRequest(socialPlatform, name, code);
    }
}
