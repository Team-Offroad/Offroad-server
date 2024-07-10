package site.offload.offloadserver.api.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    SOCIAL_LOGIN_SUCCESS("로그인 요청 성공"),
    MEMBER_ADVENTURE_INFORMATION_SUCCESS("모험 정보 조회 성공");
    private final String message;
}
