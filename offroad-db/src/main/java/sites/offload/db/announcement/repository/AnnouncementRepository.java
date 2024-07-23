package sites.offload.db.announcement.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.announcement.entity.Announcement;

public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {
}
