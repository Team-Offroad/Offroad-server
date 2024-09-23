package site.offload.api.announcement.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.announcement.dto.response.AnnouncementDetailResponse;
import site.offload.api.announcement.dto.response.AnnouncementsResponse;
import site.offload.api.announcement.service.AnnouncementService;
import site.offload.db.announcement.entity.AnnouncementEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementUseCase {

    private final AnnouncementService announcementService;

    public AnnouncementsResponse getAnnouncements() {
        List<AnnouncementEntity> announcements = announcementService.getAnnouncements();
        List<AnnouncementDetailResponse> announcementsDetail = announcements.stream()
                .map(announcementEntity -> AnnouncementDetailResponse.of(announcementEntity.getTitle(), announcementEntity.getContent(), announcementEntity.isImportant(), announcementEntity.getUpdatedAt(), announcementEntity.isHasExternalLinks(), announcementEntity.getExternalLinks()))
                .toList();
        return AnnouncementsResponse.of(announcementsDetail);
    }
}
