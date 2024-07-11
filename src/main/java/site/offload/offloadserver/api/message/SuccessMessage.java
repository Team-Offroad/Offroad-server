package site.offload.offloadserver.api.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessMessage {

    //do GoogleSocialLogin
    SOCIAL_LOGIN_SUCCESS("로그인 요청 성공"),

    //check RegisteredPlaces
    CHECK_REGISTERED_PLACES_SUCCESS("장소 리스트 정보 조회 성공");

    private final String message;
}
