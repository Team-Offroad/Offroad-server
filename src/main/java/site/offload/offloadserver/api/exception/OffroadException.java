package site.offload.offloadserver.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.offload.offloadserver.api.message.CustomErrorCode;
import site.offload.offloadserver.api.message.ErrorMessage;

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
