package site.offload.api.quest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.api.quest.dto.response.QuestDetailListResponse;
import site.offload.api.quest.dto.response.QuestResponse;
import site.offload.api.response.APIErrorResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "[Quest API] 퀘스트 관련 API")
public interface QuestControllerSwagger {

    @Operation(summary = "사용자 퀘스트 정보 조회 API", description = "사용자가 진행하는, 거의 완료된 퀘스트 정보를 조회하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 퀘스트 정보 조회 완료"),
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<QuestResponse>> getQuestInformation();

    @Operation(summary = "퀘스트 목록 정보 조회 API", description = "퀘스트 정보 목록을 조회하는 API입니다.")
    @ApiResponse(responseCode = "200", description = "퀘스트 목록 정보 조회 완료")
    @ApiResponse(responseCode = "404", description = "요청에 필요한 자원 없음",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = APIErrorResponse.class),
                    examples = {
                            @ExampleObject(name = "존재하지 않는 유저", value = "{ \"message\": \"존재하지 않는 유저\", \"customErrorCode\": \"NOT_EXISTS_MEMBER\" }"),
                    }
            ))
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @Parameter(name = "isActive", description = "진행중인 퀘스트 요청시 true, 전체 목록 요청시 false", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "boolean"))
    ResponseEntity<APISuccessResponse<QuestDetailListResponse>> getQuestList(@RequestParam(value = "isActive") boolean isActive,
                                                                             @RequestParam(value = "cursor") int cursor,
                                                                             @RequestParam(value = "size") int size);
}
