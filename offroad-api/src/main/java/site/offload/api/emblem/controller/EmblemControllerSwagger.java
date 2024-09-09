package site.offload.api.emblem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.api.emblem.dto.response.EmblemsResponse;
import site.offload.api.emblem.dto.response.GainedEmblemListResponse;
import site.offload.api.response.APIErrorResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "[Emblem API] 칭호 관련 API")
public interface EmblemControllerSwagger {

    @Operation(summary = "유저의 칭호 변경 API", description = "유저의 칭호를 변경하는 API입니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "칭호 변경 완료"),
                    @ApiResponse(responseCode = "400", description = "칭호 변경 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<APISuccessResponse<Void>> updateEmblem(@RequestParam(value = "emblemCode") final String emblemCode);

    @Operation(summary = "얻은 칭호 조회 API", description = "유저가 획득한 칭호를 조회하는 API 입니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "칭호 조회 완료"),
                    @ApiResponse(responseCode = "400", description = "칭호 조회 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<APISuccessResponse<GainedEmblemListResponse>> getGainedEmblem();

    @Operation(summary = "칭호 조회 API", description = "칭호 목록을 조회하는 API 입니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "칭호 조회 완료"),
                    @ApiResponse(responseCode = "400", description = "칭호 조회 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    ResponseEntity<APISuccessResponse<EmblemsResponse>> getEmblems();
}
