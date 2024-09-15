package site.offload.api.charactermotion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import site.offload.api.charactermotion.dto.CharacterMotionsResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "CharacterMotion API", description = "캐릭터 모션 API")
public interface CharacterMotionControllerSwagger {

    @Operation(summary = "캐릭터 모션 목록 조회 API", description = "캐릭터 모션 목록을 조회할 수 있는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "캐릭터 모션 목록 조회 성공")
    ResponseEntity<APISuccessResponse<CharacterMotionsResponse>> getMotions(
            @Parameter(name = "characterId", description = "캐릭터 아이디", in = ParameterIn.PATH, required = true, schema = @Schema(type = "Integer"))
            Integer characterId);
}
