package site.offload.offloadserver.api.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SOCIAL_LOGIN_SUCCESS("로그인 요청 성공"),

    ACCESS_TOKEN_REFRESH_SUCCESS("Access Token 재발급 성공"),
    MEMBER_ADVENTURE_INFORMATION_SUCCESS("모험 정보 조회 성공"),
    CHECK_REGISTERED_PLACES_SUCCESS("장소 리스트 정보 조회 성공"),
    MEMBER_PROFILE_UPDATE_SUCCESS("프로필 업데이트 성공"),
    CHECK_DUPLICATED_NICKNAME("닉네임 중복 확인 완료"),
    MEMBER_CURRENT_EMBLEM_UPDATE_SUCCESS("칭호 변경 성공");
    CHOOSE_CHARACTER_SUCCESS("캐릭터 선택 완료");
    private final String message;
}
