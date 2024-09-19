package site.offload.api.announcement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record AnnouncementsResponse(
        @Schema(description = "공지사항 목록")
        List<AnnouncementDetailResponse> announcements
) {
    public static AnnouncementsResponse of(List<AnnouncementDetailResponse> announcements) {
        return new AnnouncementsResponse(announcements);
    }
}
