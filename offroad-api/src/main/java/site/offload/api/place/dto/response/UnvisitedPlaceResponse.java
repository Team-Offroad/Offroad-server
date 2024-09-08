package site.offload.api.place.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import site.offload.enums.place.PlaceCategory;

public record UnvisitedPlaceResponse(
        @Schema(description = "장소 ID", example = "1")
        Long id,
        @Schema(description = "장소 이름", example = "테스트 장소")
        String name,
        @Schema(description = "장소 주소", example = "서울시 강남구")
        String address,
        @Schema(description = "장소 간단 소개", example = "테스트 장소입니다.")
        String shortIntroduction,
        @Schema(description = "장소 카테고리", example = "RESTAURANT")
        PlaceCategory placeCategory,
        @Schema(description = "장소 위도", example = "37.123456")
        double latitude,
        @Schema(description = "장소 경도", example = "127.123456")
        double longitude,
        @Schema(description = "장소 방문 횟수", example = "1")
        Long visitCount,
        @Schema(description = "장소 카테고리 이미지 URL", example = "https://test.com")
        String categoryImageUrl
) {
    public static UnvisitedPlaceResponse of(
            Long id,
            String name,
            String address,
            String shortIntroduction,
            PlaceCategory placeCategory,
            double latitude,
            double longitude,
            Long visitCount,
            String categoryImageUrl
    ) {
        return new UnvisitedPlaceResponse(id, name, address, shortIntroduction, placeCategory, latitude, longitude, visitCount, categoryImageUrl);
    }
}
