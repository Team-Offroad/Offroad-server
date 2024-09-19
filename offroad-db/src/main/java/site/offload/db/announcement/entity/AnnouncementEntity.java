package site.offload.db.announcement.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.db.BaseTimeEntity;

//공지사항
@Entity
@Table(name = "announcement")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnnouncementEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean isImportant;
}
