package site.offload.offloadserver.db.announcement.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.announcement.entity.Announcement;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
}
