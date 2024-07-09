package site.offload.offloadserver.db.member.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.BaseTimeEntity;

//로그인 유저
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

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

    @Column(nullable = false)
    private String currentCharacterName;

    @Column(nullable = false)
    private String currentEmblemName;

    @Builder
    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
