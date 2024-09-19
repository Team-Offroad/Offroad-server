package site.offload.api.announcement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.db.announcement.entity.AnnouncementEntity;
import site.offload.db.announcement.repository.AnnouncementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public List<AnnouncementEntity> getAnnouncements() {
        return announcementRepository.findAll();
    }
}
