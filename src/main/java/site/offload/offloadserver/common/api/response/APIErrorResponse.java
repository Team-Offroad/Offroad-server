package site.offload.offloadserver.common.api.response;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record APIErrorResponse(
        String message
) {
    public static ResponseEntity<APIErrorResponse> of(int statusCode, String message) {
        return ResponseEntity.status(statusCode).body(new APIErrorResponse(message));
    }
}
