package site.offload.offloadserver.db.announcement.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import site.offload.offloadserver.db.BaseTimeEntity;

//공지사항
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}
