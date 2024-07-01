package site.offload.offloadserver.api.response;

import org.springframework.http.ResponseEntity;

public record APIErrorResponse(
        String message
) {
    public static ResponseEntity<APIErrorResponse> of(final int statusCode, final String message) {
        return ResponseEntity.status(statusCode).body(new APIErrorResponse(message));
    }
}
