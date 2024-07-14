package site.offload.offloadserver.external.oauth.google.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record GoogleAuthResponse(
        String accessToken,
        String scope,
        String id_token
) {
    public static GoogleAuthResponse of(String accessToken, String scope, String id_token){
        return new GoogleAuthResponse(accessToken, scope, id_token);
    }
}
