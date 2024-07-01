package site.offload.offloadserver.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.offload.offloadserver.api.response.APIErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OffroadException.class)
    public ResponseEntity<APIErrorResponse> handleCustomException(final OffroadException exception) {
        return APIErrorResponse.of(exception.getHttpStatus().value(), exception.getMessage());
    }
}
