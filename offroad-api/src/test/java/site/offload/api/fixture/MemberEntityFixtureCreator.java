package site.offload.api.fixture;

import site.offload.dbjpa.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;

public class MemberEntityFixtureCreator {

    public static MemberEntity createMemberEntity(String sub, String email, SocialPlatform socialPlatform, String name) {
        return MemberEntity.builder()
                .sub(sub)
                .email(email)
                .socialPlatform(socialPlatform)
                .name(name)
                .build();
    }
}
