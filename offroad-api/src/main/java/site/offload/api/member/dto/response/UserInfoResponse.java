package site.offload.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserInfoResponse(
        @Schema(description = "닉네임", example = "nickname")
        String nickname,
        @Schema(description = "현재 엠블럼", example = "emblem")
        String currentEmblem,
        @Schema(description = "누적 일수", example = "1")
        long elapsedDay,
        @Schema(description = "완료 퀘스트 수", example = "1")
        long completeQuestCount,
        @Schema(description = "방문한 장소 수", example = "1")
        long visitedPlaceCount,
        @Schema(description = "현재 선택한 캐릭터의 이미지 경로", example = "example.com")
        String characterImageUrl
) {

    public static UserInfoResponse of(
            String nickname,
            String currentEmblem,
            Long elapsedDay,
            Long completeQuestCount,
            Long visitedPlaceCount,
            String characterImageUrl
    ) {
        return new UserInfoResponse(
                nickname,
                currentEmblem,
                elapsedDay,
                completeQuestCount,
                visitedPlaceCount,
                characterImageUrl
        );
    }
}
