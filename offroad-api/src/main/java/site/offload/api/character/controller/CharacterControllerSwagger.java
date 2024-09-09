package site.offload.api.character.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import site.offload.api.character.dto.response.CharacterDetailResponse;
import site.offload.api.character.dto.response.StartCharactersResponse;
import site.offload.api.response.APISuccessResponse;


@Tag(name = "[Character API] 오프로드 캐릭터 관련 API")
public interface CharacterControllerSwagger {

    @Operation(summary = "캐릭터 조회 API", description = "캐릭터 목록 조회 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "캐릭터 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "캐릭터 목록 조회 실패", content = @Content(schema = @Schema(implementation = APISuccessResponse.class)))
            }
    )
    ResponseEntity<APISuccessResponse<StartCharactersResponse>> getStartCharacters();

    @Operation(summary = "캐릭터 상세 목록 조회 API", description = "캐릭터 상세 목록 조회 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "캐릭터 상세 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "캐릭터 상세 목록 조회 실패", content = @Content(schema = @Schema(implementation = APISuccessResponse.class)))
            }
    )
    ResponseEntity<APISuccessResponse<CharacterDetailResponse>> getCharacterDetail(Integer characterId);
}
