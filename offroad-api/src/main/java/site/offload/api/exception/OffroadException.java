package site.offload.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.offload.enums.response.CustomErrorCode;
import site.offload.enums.response.ErrorMessage;

@Getter
public class OffroadException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;
    private final CustomErrorCode customErrorCode;

    public OffroadException(final ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.message = errorMessage.getMessage();
        this.httpStatus = errorMessage.getHttpStatus();
        this.customErrorCode = errorMessage.getCustomErrorCode();
    }
}
