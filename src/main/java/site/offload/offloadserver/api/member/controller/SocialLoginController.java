package site.offload.offloadserver.api.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.offload.offloadserver.api.member.dto.request.AppleSocialLoginRequest;
import site.offload.offloadserver.api.member.dto.request.GoogleSocialLoginRequest;
import site.offload.offloadserver.api.member.service.SocialLoginService;
import site.offload.offloadserver.api.message.SuccessMessage;
import site.offload.offloadserver.api.response.APISuccessResponse;
import site.offload.offloadserver.common.jwt.TokenResponse;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth/login")
public class SocialLoginController {

    private final SocialLoginService socialLoginService;

    @PostMapping("/google")
    public ResponseEntity<APISuccessResponse<TokenResponse>> login(
            @RequestBody GoogleSocialLoginRequest socialLoginRequest
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return APISuccessResponse.of(HttpStatus.CREATED.value(), SuccessMessage.SOCIAL_LOGIN_SUCCESS.getMessage(), socialLoginService.googleLogin(socialLoginRequest));
    }
    @PostMapping("/apple")
    public ResponseEntity<APISuccessResponse<TokenResponse>> appleLogin(
            @RequestBody AppleSocialLoginRequest appleSocialLoginRequest
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return APISuccessResponse.of(HttpStatus.CREATED.value(), SuccessMessage.SOCIAL_LOGIN_SUCCESS.getMessage(), socialLoginService.appleLogin(appleSocialLoginRequest));
    }

}
