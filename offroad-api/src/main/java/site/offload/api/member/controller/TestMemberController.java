package site.offload.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.exception.BadRequestException;
import site.offload.api.member.dto.request.TestMemberDeleteRequest;
import site.offload.api.member.usecase.TestMemberDeleteUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.ErrorMessage;
import site.offload.enums.response.SuccessMessage;

import java.util.Objects;

@RestController
@RequestMapping("/dev/api")
@RequiredArgsConstructor
public class TestMemberController {

    private final TestMemberDeleteUseCase testMemberDeleteUseCase;
    private final Environment environment;

    @DeleteMapping("/members")
    public ResponseEntity<APISuccessResponse<Void>> deleteTestMember(@RequestBody TestMemberDeleteRequest request) {
        if (!Objects.equals(environment.getActiveProfiles()[0], "dev")) {
            throw new BadRequestException(ErrorMessage.ERROR_MESSAGE);
        }
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        testMemberDeleteUseCase.deleteTestMember(memberId, request);
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.DELETE_TEST_MEMBER_SUCCESS.getMessage(), null);
    }
}
