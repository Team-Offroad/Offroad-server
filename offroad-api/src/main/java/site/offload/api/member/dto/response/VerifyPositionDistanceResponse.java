package site.offload.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import site.offload.api.quest.dto.response.CompleteQuestResponse;

import java.util.List;

public record VerifyPositionDistanceResponse(
        @Schema(description = "유효한 위치 여부", example = "true")
        boolean isValidPosition,
        @Schema(description = "캐릭터 이미지 URL", example = "https://test.com/test.jpg")
        String successCharacterImageUrl,
        @Schema(description = "완료한 퀘스트 목록")

        List<CompleteQuestResponse> completeQuestList) {

    public static VerifyPositionDistanceResponse of(boolean isValidPosition, String characterImageUrl, List<CompleteQuestResponse> completeQuestList) {
        return new VerifyPositionDistanceResponse(isValidPosition, characterImageUrl, completeQuestList);
    }
}
