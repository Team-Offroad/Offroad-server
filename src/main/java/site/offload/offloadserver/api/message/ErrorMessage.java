package site.offload.offloadserver.api.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    /* 400 Bad Request */
    BAD_REQUEST("bad request", HttpStatus.BAD_REQUEST);

    /* 401 UnAuthorized */

    /* 403 Forbidden */

    /* 500 Internal Server Error */

    private final String message;
    private final HttpStatus httpStatus;
}
