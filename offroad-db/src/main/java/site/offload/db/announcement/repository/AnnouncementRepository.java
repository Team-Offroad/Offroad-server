package site.offload.db.announcement.repository;


import org.springframework.data.repository.CrudRepository;
import site.offload.db.announcement.entity.AnnouncementEntity;

public interface AnnouncementRepository extends CrudRepository<AnnouncementEntity, Long> {
}
