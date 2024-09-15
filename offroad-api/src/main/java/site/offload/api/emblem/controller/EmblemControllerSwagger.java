package site.offload.api.emblem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.api.emblem.dto.response.EmblemsResponse;
import site.offload.api.emblem.dto.response.GainedEmblemListResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "Emblem API", description = "칭호 관련 API")
public interface EmblemControllerSwagger {

    @Operation(summary = "칭호 변경 API", description = "유저의 칭호를 변경하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "204", description = "칭호 변경 완료")
    ResponseEntity<APISuccessResponse<Void>> updateEmblem(
            @Parameter(name = "emblemCode", description = "칭호 코드", in = ParameterIn.QUERY, required = true, schema = @Schema(type = "String")) final String emblemCode);

    @Operation(summary = "획득 칭호 목록 조회 API", description = "유저가 획득한 칭호를 조회하는 API 입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "칭호 조회 완료")
    ResponseEntity<APISuccessResponse<GainedEmblemListResponse>> getGainedEmblem();

    @Operation(summary = "전체 칭호 목록 조회 API", description = "전체 칭호 목록을 조회하는 API 입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "칭호 조회 완료")
    ResponseEntity<APISuccessResponse<EmblemsResponse>> getEmblems();
}
