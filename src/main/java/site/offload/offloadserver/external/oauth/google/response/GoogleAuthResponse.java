package site.offload.offloadserver.external.oauth.google.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record GoogleAuthResponse(
        String accessToken,
        String refreshToken
) {
    public static GoogleAuthResponse of(String accessToken, String refreshToken){
        return new GoogleAuthResponse(accessToken, refreshToken);
    }
}
