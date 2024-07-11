package site.offload.offloadserver.db.member.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.api.member.dto.request.SocialPlatform;
import site.offload.offloadserver.db.BaseTimeEntity;

//로그인 유저
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    private static final String DEFAULT_EMBLEM_NAME = "오프로드 스타터";

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
    private String currentCharacterName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sub;

    @Column(nullable = false)
    private String currentEmblemName = DEFAULT_EMBLEM_NAME;

    @Enumerated(EnumType.STRING)
    private SocialPlatform socialPlatform;

    @Builder
    public Member(String name, String email, String sub, SocialPlatform socialPlatform) {
        this.name = name;
        this.email = email;
        this.sub = sub;
        this.socialPlatform = socialPlatform;
    }
}
