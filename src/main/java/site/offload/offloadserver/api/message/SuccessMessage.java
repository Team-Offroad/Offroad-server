package site.offload.offloadserver.api.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SOCIAL_LOGIN_SUCCESS("로그인 요청 성공");

    private final String message;
}
