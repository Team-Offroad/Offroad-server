package site.offload.dbjpa.announcement.repository;


import org.springframework.data.repository.CrudRepository;
import site.offload.dbjpa.announcement.entity.AnnouncementEntity;

public interface AnnouncementRepository extends CrudRepository<AnnouncementEntity, Long> {
}
