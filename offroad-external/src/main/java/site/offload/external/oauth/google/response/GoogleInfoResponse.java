package site.offload.external.oauth.google.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleInfoResponse(
        String id,
        String name,
        String email
) {
    public static GoogleInfoResponse of(String id, String name, String email) {
        return new GoogleInfoResponse(id, name, email);
    }
}
