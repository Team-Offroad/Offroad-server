package site.offload.offloadserver.api.character.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import site.offload.offloadserver.api.character.dto.response.CharacterListResponse;
import site.offload.offloadserver.api.response.APISuccessResponse;

public interface CharacterControllerSwagger {

    @Operation(summary = "캐릭터 조회 API", description = "캐릭터 목록 조회 API")
    @ApiResponse(responseCode = "200",
            description = "캐릭터 목록 조회 성공",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = APISuccessResponse.class)))
    ResponseEntity<APISuccessResponse<CharacterListResponse>> getCharacters();
}
