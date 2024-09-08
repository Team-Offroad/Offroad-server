package site.offload.api.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import site.offload.api.member.dto.request.TestMemberDeleteRequest;
import site.offload.api.response.APISuccessResponse;


@Tag(name = "TestMember API", description = "테스트 멤버 API(dev 환경에서만 동작합니다.)")
public interface TestMemberControllerSwagger {

    @Operation(summary = "테스트 멤버 삭제 API", description = "테스트 멤버를 삭제하는 API")
    @ApiResponse(responseCode = "200", description = "테스트 멤버 삭제 성공")
    ResponseEntity<APISuccessResponse<Void>> deleteTestMember(@RequestBody TestMemberDeleteRequest request);
}
