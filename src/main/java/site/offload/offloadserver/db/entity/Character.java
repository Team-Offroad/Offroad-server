package site.offload.offloadserver.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

//캐릭터
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String characterBaseImageUrl;

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String CharacterCode;

    @Builder
    private Character(String name, String description, String characterBaseImageUrl) {
        this.name = name;
        this.description = description;
        this.characterBaseImageUrl = characterBaseImageUrl;
    }
}
