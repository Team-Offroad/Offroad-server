package site.offload.offloadserver.api.emblem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.offloadserver.api.response.APISuccessResponse;

public interface EmblemControllSwawgger {

    @Operation(summary = "유저의 칭호 변경 API", description = "유저의 칭호를 변경하는 API입니다.")
    @ApiResponse(responseCode = "200",
            description = "칭호 변경 완료",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    ResponseEntity<APISuccessResponse<Void>> updateEmblem(@RequestParam(value = "emblemCode") final String emblemCode);
}
