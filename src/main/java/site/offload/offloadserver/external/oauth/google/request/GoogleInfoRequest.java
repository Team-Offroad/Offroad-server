package site.offload.offloadserver.external.oauth.google.request;

import site.offload.offloadserver.external.oauth.google.response.GoogleInfoResponse;

public record GoogleInfoRequest(
        String accessToken
) {
    public static GoogleInfoRequest of(String accessToken) {
        return new GoogleInfoRequest(accessToken);
    }
}
