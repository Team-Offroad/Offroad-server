package site.offload.api.announcement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AnnouncementDetailResponse(
        @Schema(description = "공지사항 제목", example = "제목")
        String title,
        @Schema(description = "공지사항 내용", example = "내용")
        String content,
        @Schema(description = "중요 공지사항 여부", example = "true")
        boolean isImportant,
        @Schema(description = "수정 일자", example = "2024-07-16 21:59:12.000000")
        LocalDateTime updateAt
) {
    public static AnnouncementDetailResponse of(String title, String content, boolean isImportant, LocalDateTime updateAt) {
        return new AnnouncementDetailResponse(title, content, isImportant, updateAt);
    }
}
