package site.offload.offloadserver.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.offloadserver.api.member.dto.response.TokenReissueResponse;
import site.offload.offloadserver.api.member.service.MemberUseCase;
import site.offload.offloadserver.api.message.SuccessMessage;
import site.offload.offloadserver.api.response.APISuccessResponse;
import site.offload.offloadserver.common.auth.PrincipalHandler;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController implements TokenControllerSwagger {

    private final PrincipalHandler principalHandler;
    private final MemberUseCase memberUseCase;

    @PostMapping("/auth/refresh")
    public ResponseEntity<APISuccessResponse<TokenReissueResponse>> refreshToken(@RequestHeader("Authorization") String tokenHeaderValue) {
        final String refreshToken = tokenHeaderValue.substring("Bearer ".length());
        final Long memberId = principalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.CREATED.value(), SuccessMessage.ACCESS_TOKEN_REFRESH_SUCCESS.getMessage(), memberUseCase.reissueTokens(memberId, refreshToken));
    }
}
