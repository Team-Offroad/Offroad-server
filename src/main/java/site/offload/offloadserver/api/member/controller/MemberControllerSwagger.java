package site.offload.offloadserver.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.offloadserver.api.response.APISuccessResponse;

public interface MemberControllerSwagger {

    @Operation(summary = "모험 정보 조회 API", description = "메인 홈에서 모험 정보 조회(닉네임, 칭호, 로티 url)하는 API입니다.")
    @ApiResponse(responseCode = "200",
            description = "모험 정보 조회 API",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    APISuccessResponse getAdventureInformation(@RequestParam(value = "category") String category,
                                                 @RequestParam(value = "characterId") Integer characterId);
}
