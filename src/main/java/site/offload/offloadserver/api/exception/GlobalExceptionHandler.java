package site.offload.offloadserver.api.exception;

import io.sentry.Sentry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.offload.offloadserver.api.response.APIErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIErrorResponse> handleBadRequestException(final RuntimeException exception) {
        return APIErrorResponse.of(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<APIErrorResponse> handleUnauthorizedException(final RuntimeException exception) {
        return APIErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<APIErrorResponse> handleForbiddenException(final RuntimeException exception) {
        return APIErrorResponse.of(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleNotFoundException(final RuntimeException exception) {
        return APIErrorResponse.of(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler(OffroadException.class)
    public ResponseEntity<APIErrorResponse> handleCustomException(final OffroadException exception) {
        Sentry.captureException(exception);
        return APIErrorResponse.of(exception.getHttpStatus().value(), exception.getMessage(), exception.getCustomErrorCode());
    }
}
