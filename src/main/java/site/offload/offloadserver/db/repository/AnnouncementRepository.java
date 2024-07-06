package site.offload.offloadserver.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.offloadserver.db.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}
