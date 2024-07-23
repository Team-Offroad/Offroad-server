package site.offload.api.response;

import org.springframework.http.ResponseEntity;

public record APISuccessResponse<T>(
        String message,
        T data
) {
    public static <T> ResponseEntity<APISuccessResponse<T>> of(int statusCode, String message, T data) {
        return ResponseEntity.status(statusCode).body(new APISuccessResponse<T>(message, data));
    }
}
