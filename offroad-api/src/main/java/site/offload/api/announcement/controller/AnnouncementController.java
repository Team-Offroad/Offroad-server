package site.offload.api.announcement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.announcement.dto.response.AnnouncementsResponse;
import site.offload.api.announcement.usecase.AnnouncementUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequestMapping("/api/announcement")
@RequiredArgsConstructor
public class AnnouncementController implements AnnouncementControllerSwagger {

    private final AnnouncementUseCase announcementUseCase;

    @GetMapping
    public ResponseEntity<APISuccessResponse<AnnouncementsResponse>> getAnnouncements() {

        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.GET_ANNOUNCEMENTS_SUCCESS.getMessage(), announcementUseCase.getAnnouncements());
    }
}
