package site.offload.offloadserver.db.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

//로그인 유저
@Entity
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
    private Member(String nickName, String name, String email, MemberGender gender, int age) {
        this.nickName = nickName;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }
}
