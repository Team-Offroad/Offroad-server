package site.offload.api.exception;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.offload.api.response.APIErrorResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIErrorResponse> handleBadRequestException(final OffroadException exception) {
        Sentry.captureException(exception);
        return APIErrorResponse.of(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getCustomErrorCode());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<APIErrorResponse> handleUnauthorizedException(final OffroadException exception) {
        Sentry.captureException(exception);
        return APIErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), exception.getMessage(), exception.getCustomErrorCode());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<APIErrorResponse> handleForbiddenException(final OffroadException exception) {
        Sentry.captureException(exception);
        return APIErrorResponse.of(HttpStatus.FORBIDDEN.value(), exception.getMessage(), exception.getCustomErrorCode());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleNotFoundException(final OffroadException exception) {
        Sentry.captureException(exception);
        return APIErrorResponse.of(HttpStatus.NOT_FOUND.value(), exception.getMessage(), exception.getCustomErrorCode());
    }

    @ExceptionHandler(OffroadException.class)
    public ResponseEntity<APIErrorResponse> handleCustomException(final OffroadException exception) {
        Sentry.captureException(exception);
        return APIErrorResponse.of(exception.getHttpStatus().value(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorResponse> handleException(final Exception exception) {
        Sentry.captureException(exception);
        return APIErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }
}
