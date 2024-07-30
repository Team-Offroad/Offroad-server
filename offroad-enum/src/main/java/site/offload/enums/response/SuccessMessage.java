package site.offload.enums.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SOCIAL_LOGIN_SUCCESS("로그인 요청 성공"),
    SIGN_OUT_SUCCESS("로그아웃 성공"),
    DELETE_TEST_MEMBER_SUCCESS("테스트 회원 삭제 성공"),

    ACCESS_TOKEN_REFRESH_SUCCESS("Access Token 재발급 성공"),
    MEMBER_ADVENTURE_INFORMATION_SUCCESS("모험 정보 조회 성공"),
    CHECK_REGISTERED_PLACES_SUCCESS("장소 리스트 정보 조회 성공"),
    MEMBER_PROFILE_UPDATE_SUCCESS("프로필 업데이트 성공"),
    CHECK_DUPLICATED_NICKNAME_SUCCESS("닉네임 중복 확인 완료"),
    MEMBER_CURRENT_EMBLEM_UPDATE_SUCCESS("칭호 변경 성공"),
    CHOOSE_CHARACTER_SUCCESS("캐릭터 선택 완료"),
    GET_GAINED_EMBLEM_SUCCESS("칭호 조회 완료"),
    GET_CHARACTERS_LIST_SUCCESS("캐릭터 목록 조회 완료"),
    GET_QUEST_INFORMATION_SUCCESS("퀘스트 정보 조회 성공"),
    AUTHENTICATE_ADVENTURE_REQUEST_SUCCESS("탐험 인증 요청 성공");
    private final String message;
}
