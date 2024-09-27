package site.offload.api.character.controller;

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
import site.offload.api.character.dto.response.CharacterDetailResponse;
import site.offload.api.character.dto.response.StartCharactersResponse;
import site.offload.api.member.dto.response.ChooseCharacterResponse;
import site.offload.api.member.dto.response.GainedCharactersResponse;
import site.offload.api.response.APIErrorResponse;
import site.offload.api.response.APISuccessResponse;


@Tag(name = "[Character API] 오프로드 캐릭터 관련 API")
public interface CharacterControllerSwagger {

    @Operation(summary = "시작 캐릭터 조회 API", description = "시작 캐릭터 목록 조회 API, 사용자가 처음 로그인 후 캐릭터를 선택할 때 조회하는 API입니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "캐릭터 목록 조회 성공"),
            }
    )
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<StartCharactersResponse>> getStartCharacters();

    @Operation(summary = "캐릭터 상세 목록 조회 API", description = "캐릭터 상세 목록 조회 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "캐릭터 상세 목록 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "요청에 필요한 자원이 없음", content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "존재하지 않는 캐릭터", value = "{ \"message\": \"존재하지 않는 캐릭터\", \"customErrorCode\": \"NOT_EXISTS_CHARACTER\" }", description = "해당 캐릭터가 존재하지 않음"),
                            })),
            }
    )
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @Parameter(name = "characterId", description = "캐릭터 아이디", in = ParameterIn.PATH, required = true, schema = @Schema(type = "Integer"))
    ResponseEntity<APISuccessResponse<CharacterDetailResponse>> getCharacterDetail(@PathVariable(value = "characterId") Integer characterId);

    @Operation(summary = "캐릭터 선택 API", description = "캐릭터를 선택하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐릭터 선택 성공"),
            @ApiResponse(responseCode = "400", description = "캐릭터 선택 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    @Parameter(name = "characterId", description = "캐릭터 아이디", in = ParameterIn.PATH, required = true, schema = @Schema(type = "Integer"))
    ResponseEntity<APISuccessResponse<ChooseCharacterResponse>> chooseCharacter(@PathVariable(value = "characterId") Integer characterId);

    @Operation(summary = "전체 캐릭터 목록 조회 API", description = "전체 캐릭터 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "캐릭터 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "캐릭터 목록 조회 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<GainedCharactersResponse>> getCharacters();
}
