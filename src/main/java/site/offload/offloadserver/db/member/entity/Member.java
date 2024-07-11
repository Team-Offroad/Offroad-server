package site.offload.offloadserver.db.member.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.api.member.dto.request.MemberProfileUpdateRequest;
import site.offload.offloadserver.api.member.dto.request.SocialPlatform;
import site.offload.offloadserver.db.BaseTimeEntity;
import site.offload.offloadserver.db.emblem.entity.Emblem;
import site.offload.offloadserver.db.member.embeddable.Birthday;

//로그인 유저
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    private static final Emblem DEFAULT_EMBLEM_NAME = Emblem.DEFAULT_EMBLEM;

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
    @Enumerated(EnumType.STRING)
    private Emblem currentEmblemName = DEFAULT_EMBLEM_NAME;

    @Enumerated(EnumType.STRING)
    private SocialPlatform socialPlatform;

    @Column(nullable = false)
    private int birthYear = 0;

    @Column(nullable = false)
    private int birthMonth = 0;

    @Column(nullable = false)
    private int birthDay = 0;

    @Embedded
    private Birthday birthday;

    @Builder
    public Member(String name, String email, String sub, SocialPlatform socialPlatform) {
        this.name = name;
        this.email = email;
        this.sub = sub;
        this.socialPlatform = socialPlatform;
    }

    public void updateProfile(MemberProfileUpdateRequest memberProfileUpdateRequest) {
        this.nickName = memberProfileUpdateRequest.nickName();
        this.birthYear = memberProfileUpdateRequest.year();
        this.birthMonth = memberProfileUpdateRequest.month();
        this.birthDay = memberProfileUpdateRequest.day();
        this.gender = memberProfileUpdateRequest.gender();
    }

    public void updateEmblem(Emblem emblem) {
        this.currentEmblemName = emblem;
    }

}
