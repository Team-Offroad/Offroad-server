package sites.offload.db.announcement.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.announcement.entity.AnnouncementEntity;

public interface AnnouncementRepository extends CrudRepository<AnnouncementEntity, Long> {
}
