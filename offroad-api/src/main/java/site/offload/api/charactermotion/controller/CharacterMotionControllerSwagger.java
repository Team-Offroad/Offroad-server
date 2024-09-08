package site.offload.api.charactermotion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import site.offload.api.charactermotion.dto.CharacterMotionsResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "CharacterMotion API", description = "캐릭터 모션 API")
public interface CharacterMotionControllerSwagger {

    @Operation(summary = "캐릭터 모션 목록 조회 API", description = "캐릭터 모션 목록 조회 API")
    @ApiResponse(responseCode = "200", description = "캐릭터 모션 목록 조회 성공")
    ResponseEntity<APISuccessResponse<CharacterMotionsResponse>> getMotions(Integer characterId);
}
