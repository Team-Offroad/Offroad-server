package site.offload.api.adventure.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import site.offload.api.quest.dto.response.CompleteQuestResponse;

import java.util.List;

public record VerifyQrcodeResponse(
        @Schema(description = "인증코드 일치 여부", example = "true")
        boolean isQRMatched,
        @Schema(description = "캐릭터 이미지 URL", example = "https://test.com/test.jpg")
        String characterImageUrl,

        @Schema(description = "완료한 퀘스트 목록")
        List<CompleteQuestResponse> completeQuestList
) {

    public static VerifyQrcodeResponse of(boolean isQRMatched, String characterImageUrl, List<CompleteQuestResponse> completeQuestList) {
        return new VerifyQrcodeResponse(isQRMatched, characterImageUrl, completeQuestList);
    }
}
