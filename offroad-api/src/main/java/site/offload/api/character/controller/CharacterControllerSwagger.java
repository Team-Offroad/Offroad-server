package site.offload.api.character.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import site.offload.api.character.dto.response.CharacterDetailResponse;
import site.offload.api.character.dto.response.StartCharactersResponse;
import site.offload.api.response.APISuccessResponse;


@Tag(name = "Character API", description = "캐릭터 관련 API")
public interface CharacterControllerSwagger {

    @Operation(summary = "시작 캐릭터 조회 API", description = "시작 시 선택할 수 있는 캐릭터를 조회하는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "캐릭터 목록 조회 성공")
    ResponseEntity<APISuccessResponse<StartCharactersResponse>> getStartCharacters();

    @Operation(summary = "캐릭터 상세 정보 조회 API", description = "캐릭터의 정보를 상세 조회할 수 있는 API입니다.")
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "String"))
    @ApiResponse(responseCode = "200", description = "캐릭터 상세 목록 조회 성공")
    ResponseEntity<APISuccessResponse<CharacterDetailResponse>> getCharacterDetail(
            @Parameter(name = "characterId", description = "캐릭터 아이디", in = ParameterIn.PATH, required = true, schema = @Schema(type = "Integer"))
            Integer characterId
    );
}
