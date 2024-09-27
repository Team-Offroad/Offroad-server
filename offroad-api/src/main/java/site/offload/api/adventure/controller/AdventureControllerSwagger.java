package site.offload.api.adventure.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import site.offload.api.adventure.dto.request.AuthAdventureRequest;
import site.offload.api.adventure.dto.request.AuthPositionRequest;
import site.offload.api.adventure.dto.response.VerifyPositionDistanceResponse;
import site.offload.api.adventure.dto.response.VerifyQrcodeResponse;
import site.offload.api.response.APIErrorResponse;
import site.offload.api.response.APISuccessResponse;

@Tag(name = "[Adventure API] 탐험 관련 API")
public interface AdventureControllerSwagger {

    @Operation(summary = "QR 탐험 인증 API", description = "QR코드로 탐험인증 하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "탐험 인증 성공"),
            @ApiResponse(responseCode = "400", description = "탐험 인증 실패", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<VerifyQrcodeResponse>> authAdventure(final @RequestBody AuthAdventureRequest request);

    @Operation(summary = "위치 탐험 인증 API", description = "위치 정보로 탐험인증 하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "탐험 인증 성공"),
            @ApiResponse(responseCode = "404", description = "요청에 필요한 자원 없음",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "존재하지 않는 장소", value = "{ \"message\": \"존재하지 않는 장소\", \"customErrorCode\": \"NOT_EXISTS_PLACE\" }"),
                                    @ExampleObject(name = "존재하지 않는 캐릭터", value = "{ \"message\": \"존재하지 않는 캐릭터\", \"customErrorCode\": \"NOT_EXISTS_CHARACTER\" }"),
                                    @ExampleObject(name = "존재하지 않는 유저", value = "{ \"message\": \"존재하지 않는 유저\", \"customErrorCode\": \"NOT_EXISTS_MEMBER\" }"),
                                    @ExampleObject(name = "존재하지 않는 퀘스트", value = "{ \"message\": \"존재하지 않는 퀘스트\", \"customErrorCode\": \"NOT_EXISTS_QUEST\" }")
                            }
                    ))
    })
    @Parameter(name = "Authorization", description = "Bearer {access_token}", in = ParameterIn.HEADER, required = true, schema = @Schema(type = "string"))
    ResponseEntity<APISuccessResponse<VerifyPositionDistanceResponse>> authAdventureOnlyPlace(final @RequestBody AuthPositionRequest request);
}
