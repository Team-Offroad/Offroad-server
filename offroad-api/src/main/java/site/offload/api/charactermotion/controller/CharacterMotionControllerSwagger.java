package site.offload.api.charactermotion.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import site.offload.api.charactermotion.dto.CharacterMotionsResponse;
import site.offload.api.response.APIErrorResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "[CharacterMotion API] 캐릭터 모션 관련 API")
public interface CharacterMotionControllerSwagger {

    @Operation(summary = "해당 캐릭터 모션 목록 조회 API", description = "해당 캐릭터의 모션 목록을 조회 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "캐릭터 모션 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "캐릭터 모션 목록 조회 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
            }
    )
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @Parameter(name = "characterId", description = "캐릭터 아이디", in = ParameterIn.PATH, required = true, schema = @Schema(type = "Integer"))
    ResponseEntity<APISuccessResponse<CharacterMotionsResponse>> getMotions(@PathVariable(value = "characterId") Integer characterId);
}
