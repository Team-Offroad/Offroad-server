package site.offload.db.announcement.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.offload.db.util.StringListConvert;
import site.offload.db.BaseTimeEntity;

import java.util.List;

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

    @Column(nullable = false)
    private boolean hasExternalLinks;

    @Column(nullable = true)
    @Convert(converter = StringListConvert.class)
    private List<String> externalLinks;
}
