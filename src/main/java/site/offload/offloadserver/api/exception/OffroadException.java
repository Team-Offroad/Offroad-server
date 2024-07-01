package site.offload.offloadserver.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.offload.offloadserver.api.message.ErrorMessage;

@Getter
public class OffroadException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public OffroadException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.message = errorMessage.getMessage();
        this.httpStatus = errorMessage.getHttpStatus();
    }
}
