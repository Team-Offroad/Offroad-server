package site.offload.offloadserver.api.message;

public enum CustomErrorCode {
    CUSTOM_ERROR_CODE, // 예시 코드
    FAIL_REISSUE_TOKEN,
    NOT_EXISTS_MEMBER, // 존재하지 않는 유저
    NOT_EXISTS_PLACE_CATEGORY, // 존재하지 않은 장소 카테고리
    NOT_EXISTS_CHARACTER, // 존재하지 않는 캐릭터
    NOT_EXISTS_CHARACTER_MOTION, // 존재하지 않는 캐릭터 모션
    NOT_EXISTS_EMBLEM,
    INVALID_AUTHORIZATION_JWT,
    FAIL_EMBLEM_UPDATE;
}
