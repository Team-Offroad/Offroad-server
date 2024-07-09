package site.offload.offloadserver.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.offloadserver.api.member.dto.request.SocialLoginRequest;
import site.offload.offloadserver.api.member.service.SocialLoginService;
import site.offload.offloadserver.api.message.SuccessMessage;
import site.offload.offloadserver.api.response.APISuccessResponse;
import site.offload.offloadserver.common.jwt.TokenResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SocialLoginController {

    private final SocialLoginService socialLoginService;

    @PostMapping("/oauth/login")
    public ResponseEntity<APISuccessResponse<TokenResponse>> login(
            @RequestBody SocialLoginRequest socialLoginRequest
    ) {
        return APISuccessResponse.of(HttpStatus.CREATED.value(), SuccessMessage.SOCIAL_LOGIN_SUCCESS.getMessage(), socialLoginService.dologin(socialLoginRequest));
    }
}
