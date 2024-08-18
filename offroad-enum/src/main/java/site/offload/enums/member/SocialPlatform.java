package site.offload.enums.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialPlatform {
    GOOGLE("GOOGLE"),
    APPLE("APPLE"),
    KAKAO("KAKAO");
    private final String socialPlatform;
}
