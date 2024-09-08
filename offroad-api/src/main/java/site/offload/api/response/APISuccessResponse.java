package site.offload.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;

public record APISuccessResponse<T>(
        @Schema(description = "응답 메시지", example = "RESPONSE_SUCCESS")
        String message,
        @Schema(description = "응답 데이터")
        T data
) {
    public static <T> ResponseEntity<APISuccessResponse<T>> of(int statusCode, String message, T data) {
        return ResponseEntity.status(statusCode).body(new APISuccessResponse<T>(message, data));
    }
}
