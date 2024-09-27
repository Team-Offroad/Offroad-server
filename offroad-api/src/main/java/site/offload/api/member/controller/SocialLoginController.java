package site.offload.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.member.dto.request.LogoutRequest;
import site.offload.api.member.dto.response.SocialLoginResponse;
import site.offload.api.member.usecase.LogoutUseCase;
import site.offload.api.member.usecase.SocialLoginUseCase;
import site.offload.api.response.APISuccessResponse;
import site.offload.enums.response.SuccessMessage;
import site.offload.external.oauth.dto.SocialLoginRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SocialLoginController implements SocialLoginControllerSwagger {

    private final SocialLoginUseCase socialLoginUseCase;
    private final LogoutUseCase logOutUseCase;

    @PostMapping("/login")
    public ResponseEntity<APISuccessResponse<SocialLoginResponse>> login(
            @RequestBody SocialLoginRequest socialLoginRequest
    ) {
        return APISuccessResponse.of(HttpStatus.CREATED.value(), SuccessMessage.SOCIAL_LOGIN_SUCCESS.getMessage(), socialLoginUseCase.login(socialLoginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<APISuccessResponse<Void>> logout(@RequestBody final LogoutRequest request) {
        logOutUseCase.execute(request);
        return APISuccessResponse.of(HttpStatus.OK.value(), SuccessMessage.SIGN_OUT_SUCCESS.getMessage(), null);
    }
}
