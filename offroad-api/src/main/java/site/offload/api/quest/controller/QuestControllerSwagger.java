package site.offload.api.quest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.api.quest.dto.request.QuestDetailListRequest;
import site.offload.api.quest.dto.response.QuestDetailListResponse;
import site.offload.api.quest.dto.response.QuestResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "Quest API", description = "퀘스트 관련 API")
public interface QuestControllerSwagger {

    @Operation(summary = "퀘스트 정보 조회 API", description = "퀘스트 정보를 조회하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "퀘스트 정보 조회 완료")
    ResponseEntity<APISuccessResponse<QuestResponse>> getQuestInformation();

    @Operation(summary = "퀘스트 목록 조회 API", description = "퀘스트 목록 탭의 정보를 조회하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "퀘스트 목록 정보 조회 완료")
    ResponseEntity<APISuccessResponse<QuestDetailListResponse>> getQuestList(@RequestParam boolean isActive);
}
