package site.offload.external.exception;

import org.springframework.http.HttpStatus;
import site.offload.external.enums.RestClientUseCase;

public class RestClientException extends RuntimeException {

    public RestClientException(RestClientUseCase restClientUseCase, String message, HttpStatus httpStatus) {
        super(formatMessage(restClientUseCase, message, httpStatus));
    }

    private static String formatMessage(RestClientUseCase restClientUseCase, String message, HttpStatus status) {
        return "RestClientUseCase: " + restClientUseCase.toString() + "\n" +
                "Status Code: " + status.toString() + "\n" +
                "Error message: " + message;
    }
}