package site.offload.enums.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialPlatform {
    GOOGLE("google"),
    APPLE("apple");
    private final String socialPlatform;
}
