package site.offload.api.announcement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import site.offload.api.announcement.dto.response.AnnouncementsResponse;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@Tag(name = "[Announcement API] 오프로드 공지사항 관련 API")
public interface AnnouncementControllerSwagger {

    @Operation(summary = "오프로드 공지사항 조회 API", description = "오프로드 공지사항을 불러오는 API입니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "오프로드 공지사항 조회 완료"),
                    @ApiResponse(responseCode = "400", description = "오프로드 공지사항 조회 실패", content = @Content(schema = @Schema(implementation = SuccessMessage.class)))
            }
    )
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    public ResponseEntity<APISuccessResponse<AnnouncementsResponse>> getAnnouncements();
}
