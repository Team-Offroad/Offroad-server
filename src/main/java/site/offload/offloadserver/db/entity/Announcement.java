package site.offload.offloadserver.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

//공지사항
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String writer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    private Announcement(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
