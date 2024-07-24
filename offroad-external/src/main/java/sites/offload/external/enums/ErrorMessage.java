package sites.offload.external.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    /* 401 UnAuthorized */
    INVALID_EXPIRATION_JWT_EXCEPTION("기간이 만료된 토큰입니다.", HttpStatus.UNAUTHORIZED, CustomErrorCode.INVALID_EXPIRATION_JWT),
    INVALID_JWT_EXCEPTION("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED, CustomErrorCode.INVALID_AUTHORIZATION_JWT);
    private final String message;
    private final HttpStatus httpStatus;
    private final CustomErrorCode customErrorCode;
}
