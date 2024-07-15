package site.offload.offloadserver.external.oauth.google.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleAuthResponse(
        String accessToken,
        String id_token
) {
    public static GoogleAuthResponse of(String accessToken, String id_token){
        return new GoogleAuthResponse(accessToken, id_token);
    }
}
