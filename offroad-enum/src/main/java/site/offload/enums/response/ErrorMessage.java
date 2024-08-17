package site.offload.enums.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    /* 400 Bad Request */
    MEMBER_EMBLEM_UPDATE_EXCEPTION("사용자가 얻은 칭호가 아닙니다.", HttpStatus.BAD_REQUEST, CustomErrorCode.FAIL_EMBLEM_UPDATE),
    EMBLEM_CODE_NOTFOUND_EXCEPTION("존재하지 않는 칭호 코드입니다.", HttpStatus.BAD_REQUEST, CustomErrorCode.NOT_EXISTS_EMBLEM_CODE),
    NOT_ALLOWED_DISTANCE_EXCEPTION("탐험 인증 허용 오차 범위를 벗어났습니다.", HttpStatus.BAD_REQUEST, CustomErrorCode.TOO_FAR_DISTANCE),
    NOT_GAINED_CHARACTER("사용자가 얻은 캐릭터가 아닙니다.", HttpStatus.BAD_REQUEST, CustomErrorCode.NOT_GAINED_CHARACTER),
    INVALID_MEMBER_DELETE_CODE("올바르지 않은 회원 탈퇴 코드입니다.", HttpStatus.BAD_REQUEST, CustomErrorCode.INVALID_MEMBER_DELETE_CODE),
    /* 401 UnAuthorized */
    FILTER_UNAUTHORIZED_EXCEPTION("허용되지 않은 필터 인증입니다.", HttpStatus.UNAUTHORIZED, CustomErrorCode.INVALID_AUTHORIZATION_FILTER),
    SOCIAL_LOGIN_UNAUTHORIZED_EXCEPTION("응답을 받는 데 실패했습니다.", HttpStatus.UNAUTHORIZED, CustomErrorCode.INVALID_AUTHORIZATION_JWT),
    INVALID_EXPIRATION_JWT_EXCEPTION("기간이 만료된 토큰입니다.", HttpStatus.UNAUTHORIZED, CustomErrorCode.INVALID_EXPIRATION_JWT),
    INVALID_JWT_EXCEPTION("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED, CustomErrorCode.INVALID_AUTHORIZATION_JWT),
    JWT_REISSUE_EXCEPTION("토큰 재발급에 실패했습니다.", HttpStatus.UNAUTHORIZED, CustomErrorCode.FAIL_REISSUE_TOKEN),
    /* 403 Forbidden */
    MEMBER_STATUS_INACTIVE("회원 상태가 비활성되어 있습니다.", HttpStatus.FORBIDDEN, CustomErrorCode.INVALID_MEMBER_STATUS),
    /* 404 Not Found */
    MEMBER_NOTFOUND_EXCEPTION("해당 ID의 유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_MEMBER),
    CHARACTER_NOTFOUND_EXCEPTION("해당 ID의 캐릭터가 존재하지 않습니다.", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_CHARACTER),
    PLACE_CATEGORY_NOTFOUND_EXCEPTION("존재하지 않는 카테고리 유형입니다.", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_PLACE_CATEGORY),
    CHARACTER_MOTION_NOTFOUND_EXCEPTION("캐릭터 모션이 존재하지 않습니다", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_CHARACTER_MOTION),
    EMBLEM_NOTFOUND_EXCEPTION("칭호가 존재하지 않습니다.", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_EMBLEM),
    PLACE_NOTFOUND_EXCEPTION("해당 ID의 장소가 존재하지 않습니다.", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_PLACE),
    PROCEEDING_QUEST_NOTFOUND_EXCEPTION("진행 중인 해당 ID의 퀘스트가 존재하지 않습니다.", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_PROCEEDING_QUEST),
    QUEST_NOTFOUND_EXCEPTION("퀘스트가 존재하지 않습니다.", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_PLACE),
    COUPON_NOTFOUND_EXCEPTION("쿠폰이 존재하지 않습니다.", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_COUPON),
    QUEST_REWARD_NOTFOUND_EXCEPTION("퀘스트 보상이 존재하지 않습니다.", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_QUEST_REWARD),
    GAINED_COUPON_NOTFOUND_EXCEPTION("보유중인 쿠폰이 아닙니다.", HttpStatus.NOT_FOUND, CustomErrorCode.NOT_EXISTS_GAINED_COUPON),
    /* 500 Internal Server Error */
    ERROR_MESSAGE("example", HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.CUSTOM_ERROR_CODE);
    private final String message;
    private final HttpStatus httpStatus;
    private final CustomErrorCode customErrorCode;
}
