package sites.offload.db.announcement.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sites.offload.db.BaseTimeEntity;

//공지사항
@Entity
@Table(name = "announcement")
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
}
