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
    /* 403 Forbidden */

    /* 404 Not Found */
    MEMBER_NOTFOUND_EXCEPTION("해당 ID의 유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    CHARACTER_NOTFOUND_EXCEPTION("해당 ID의 캐릭터가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    PLACE_CATEGORY_NOTFOUND_EXCEPTION("존재하지 않는 카테고리 유형입니다.", HttpStatus.NOT_FOUND),
    CHARACTER_MOTION_NOTFOUND_EXCEPTION("캐릭터 모션이 존재하지 않습니다", HttpStatus.NOT_FOUND),
    /* 500 Internal Server Error */
    ERROR_MESSAGE("example",HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.CUSTOM_ERROR_CODE);
    private final String message;
    private final HttpStatus httpStatus;
    private final CustomErrorCode customErrorCode;
}
