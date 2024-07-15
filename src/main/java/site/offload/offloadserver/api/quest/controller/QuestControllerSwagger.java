package site.offload.offloadserver.api.quest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import site.offload.offloadserver.api.quest.dto.response.QuestResponse;
import site.offload.offloadserver.api.response.APISuccessResponse;

public interface QuestControllerSwagger {

    @Operation(summary = "퀘스트 정보 조회 API", description = "퀘스트 정보를 조회하는 API입니다.")
    @ApiResponse(responseCode = "200",
            description = "퀘스트 정보 조회 완료",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    public ResponseEntity<APISuccessResponse<QuestResponse>> getQuestInformation();
}
