package site.offload.offloadserver.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import site.offload.offloadserver.api.member.dto.MemberAdventureInformationResponse;
import site.offload.offloadserver.api.response.APISuccessResponse;

public interface MemberControllerSwagger {

    @Operation(summary = "모험 정보 인증 API", description = "메인 홈에서 모험 인증 정보(닉네임, 캐릭터 이미지 or 모션 이미지, 칭호)반환")
    @ApiResponse(responseCode = "200",
            description = "모험 인증 정보 요청 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    ResponseEntity<APISuccessResponse<MemberAdventureInformationResponse>> getAdventureInformation(@RequestParam(value = "category") final String category,
                                                                                                   @RequestParam(value = "characterId") final Integer characterId);
}
