package site.offload.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.auth.PrincipalHandler;
import site.offload.api.member.dto.response.TokenReissueResponse;
import site.offload.api.member.usecase.TokenUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController implements TokenControllerSwagger {

    private final TokenUseCase tokenUseCase;

    @PostMapping("/auth/refresh")
    public ResponseEntity<APISuccessResponse<TokenReissueResponse>> refreshToken(@RequestHeader("Authorization") String tokenHeaderValue) {
        final String refreshToken = tokenHeaderValue.substring("Bearer ".length());
        final Long memberId = PrincipalHandler.getMemberIdFromPrincipal();
        return APISuccessResponse.of(HttpStatus.CREATED.value(), SuccessMessage.ACCESS_TOKEN_REFRESH_SUCCESS.getMessage(), tokenUseCase.reissueTokens(memberId, refreshToken));
    }
}
