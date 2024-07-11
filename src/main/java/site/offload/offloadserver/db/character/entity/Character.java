package site.offload.offloadserver.db.character.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.BaseTimeEntity;

//캐릭터
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Character extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String characterBaseImageUrl;

    @Column(nullable = false, unique = true)
    private String CharacterCode;
}
