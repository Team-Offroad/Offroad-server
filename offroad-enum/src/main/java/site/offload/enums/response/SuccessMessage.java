package site.offload.enums.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SOCIAL_LOGIN_SUCCESS("로그인 요청 성공"),
    SIGN_OUT_SUCCESS("로그아웃 성공"),
    DELETE_TEST_MEMBER_SUCCESS("테스트 회원 삭제 성공"),
    SOFT_DELETE_MEMBER_SUCCESS("회원 탈퇴 성공"),
    CHECK_AGREE_MARKETING_SUCCESS("마케팅 정보 수신 여부 확인 완료"),
    ACCESS_TOKEN_REFRESH_SUCCESS("Access Token 재발급 성공"),
    MEMBER_ADVENTURE_INFORMATION_SUCCESS("모험 정보 조회 성공"),
    CHECK_REGISTERED_PLACES_SUCCESS("장소 리스트 정보 조회 성공"),
    MEMBER_PROFILE_UPDATE_SUCCESS("프로필 업데이트 성공"),
    CHECK_DUPLICATED_NICKNAME_SUCCESS("닉네임 중복 확인 완료"),
    MEMBER_CURRENT_EMBLEM_UPDATE_SUCCESS("칭호 변경 성공"),
    CHOOSE_CHARACTER_SUCCESS("캐릭터 선택 완료"),
    GET_GAINED_EMBLEMS_SUCCESS("보유 칭호 목록 조회 완료"),
    GET_START_CHARACTERS_SUCCESS("시작 캐릭터 목록 조회 완료"),
    GET_QUEST_INFORMATION_SUCCESS("퀘스트 정보 조회 성공"),
    GET_QUEST_DETAIL_LIST_SUCCESS("퀘스트 목록 조회 성공"),
    AUTHENTICATE_ADVENTURE_REQUEST_SUCCESS("탐험 인증 요청 성공"),
    GET_GAINED_CHARACTERS_SUCCESS("캐릭터 조회 성공"),
    GET_COUPON_LIST_SUCCESS("획득 쿠폰 조회 요청 성공"),
    GET_COUPON_APPLY_SUCCESS("획득 쿠폰 사용 요청 성공"),
    GET_EMBLEMS_SUCCESS("칭호 조회 완료"),
    GET_MOTIONS_SUCCESS("캐릭터 모션 목록 조회 성공"),
    GET_CHARACTER_DETAIL_SUCCESS("캐릭터 상세 정보 조회 성공"),
    GET_USER_INFO_SUCCESS("사용자 정보 조회 성공"),
    CHECK_UNVISITED_PLACES_SUCCESS("미방문 장소 목록 조회 성공"),;
    private final String message;
}
