package site.offload.api.member.dto.request;

public record AuthAdventureRequest(Long placeId, double longitude, double latitude, String qrCode) {
}
