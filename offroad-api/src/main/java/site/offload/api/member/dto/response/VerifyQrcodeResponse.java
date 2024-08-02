package site.offload.api.member.dto.response;

import site.offload.api.quest.dto.response.CompleteQuestResponse;

import java.util.List;

public record VerifyQrcodeResponse(boolean isQRMatched, String characterImageUrl,
                                   List<CompleteQuestResponse> completeQuestList) {

    public static VerifyQrcodeResponse of(boolean isQRMatched, String characterImageUrl, List<CompleteQuestResponse> completeQuestList) {
        return new VerifyQrcodeResponse(isQRMatched, characterImageUrl, completeQuestList);
    }
}
