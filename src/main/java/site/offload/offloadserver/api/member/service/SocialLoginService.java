package site.offload.offloadserver.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.api.member.dto.request.SocialLoginRequest;
import site.offload.offloadserver.api.member.dto.request.SocialPlatform;
import site.offload.offloadserver.common.jwt.TokenResponse;
import site.offload.offloadserver.external.oauth.google.GoogleSocialLoginService;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    private final GoogleSocialLoginService googleSocialLoginService;

    public TokenResponse dologin(SocialLoginRequest socialLoginRequest) {
        if (socialLoginRequest.socialPlatform().equals(SocialPlatform.GOOGLE)) {
            return googleSocialLoginService.login(socialLoginRequest);
        }
        return TokenResponse.of(null, null);
    }
}
