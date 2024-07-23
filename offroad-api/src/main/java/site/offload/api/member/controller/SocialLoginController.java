package site.offload.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.api.member.dto.response.SocialLoginResponse;
import site.offload.api.member.usecase.SocialLoginUseCase;
import site.offload.api.response.APISuccessResponse;
import sites.offload.enums.SuccessMessage;
import sites.offload.external.oauth.dto.SocialLoginRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth/login")
public class SocialLoginController {

    private final SocialLoginUseCase socialLoginUseCase;

    @PostMapping
    public ResponseEntity<APISuccessResponse<SocialLoginResponse>> login(
            @RequestBody SocialLoginRequest socialLoginRequest
    ) {
        return APISuccessResponse.of(HttpStatus.CREATED.value(), SuccessMessage.SOCIAL_LOGIN_SUCCESS.getMessage(), socialLoginUseCase.login(socialLoginRequest));
    }

}
