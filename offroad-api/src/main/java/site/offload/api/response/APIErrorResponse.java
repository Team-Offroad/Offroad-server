package site.offload.api.response;

import org.springframework.http.ResponseEntity;
import site.offload.enums.response.CustomErrorCode;

public record APIErrorResponse(
        String message, CustomErrorCode customErrorCode
) {
    public static ResponseEntity<APIErrorResponse> of(final int statusCode, final String message, final CustomErrorCode customErrorCode) {
        return ResponseEntity.status(statusCode).body(new APIErrorResponse(message, customErrorCode));
    }

    public static ResponseEntity<APIErrorResponse> of(final int statusCode, final String message) {
        return ResponseEntity.status(statusCode).body(new APIErrorResponse(message, null));
    }
}
