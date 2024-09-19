package site.offload.db.announcement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.announcement.entity.AnnouncementEntity;

public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {
}
