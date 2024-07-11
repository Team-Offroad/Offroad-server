package site.offload.offloadserver.api.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    /* 400 Bad Request */

    /* 401 UnAuthorized */
    JWT_UNAUTHORIZED_EXCEPTION("사용자 검증을 실패했습니다.",HttpStatus.UNAUTHORIZED, CustomErrorCode.INVALID_AUTHORIZATION_JWT),
    JWT_REISSUE_EXCEPTION("토큰 재발급에 실패했습니다.", HttpStatus.UNAUTHORIZED, CustomErrorCode.FAIL_REISSUE_TOKEN),
    /* 403 Forbidden */

    /* 404 Not Found */

    /* 500 Internal Server Error */
    ERROR_MESSAGE("example",HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.CUSTOM_ERROR_CODE);
    private final String message;
    private final HttpStatus httpStatus;
    private final CustomErrorCode customErrorCode;
}
