package sites.offload.external.oauth.dto;

import sites.offload.enums.SocialPlatform;

public record SocialLoginRequest(
        SocialPlatform socialPlatform,
        String name,
        String code
) {
    public static SocialLoginRequest of(SocialPlatform socialPlatform, String name, String code) {
        return new SocialLoginRequest(socialPlatform, name, code);
    }
}
