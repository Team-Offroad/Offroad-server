package sites.offload.db.member.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sites.offload.db.BaseTimeEntity;
import sites.offload.db.member.embeddable.Birthday;
import sites.offload.enums.Emblem;
import sites.offload.enums.MemberGender;
import sites.offload.enums.SocialPlatform;

//로그인 유저
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    private static final java.lang.String DEFAULT_EMBLEM_NAME = Emblem.OFFROAD_STARTER.getEmblemName();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 소셜 로그인 기반 정보 : name(필수), email(필수)
     */
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberGender gender;

    @Column(columnDefinition = "integer CHECK (age >= 0)")
    private int age;

    @Column(unique = true)
    private String nickName;

    @Column(nullable = true)
    private String currentCharacterName = null;

    // 소셜 플랫폼 아이디
    @Column(nullable = false, columnDefinition = "TEXT")
    private String sub;

    @Column(nullable = false)
    private String currentEmblemName = DEFAULT_EMBLEM_NAME;

    @Enumerated(EnumType.STRING)
    private SocialPlatform socialPlatform;

    @Embedded
    private Birthday birthday;

    @Builder
    public Member(String name, String email, String sub, SocialPlatform socialPlatform) {
        this.name = name;
        this.email = email;
        this.sub = sub;
        this.socialPlatform = socialPlatform;
    }

    public void updateProfile(String nickname, Birthday birthday, MemberGender gender) {
        this.nickName = nickname;
        this.birthday = birthday;
        this.gender = gender;
    }

    public void updateEmblemName(Emblem emblem) {
        this.currentEmblemName = emblem.getEmblemName();
    }

    public void chooseCharacter(String characterName) {
        this.currentCharacterName = characterName;
    }

}
