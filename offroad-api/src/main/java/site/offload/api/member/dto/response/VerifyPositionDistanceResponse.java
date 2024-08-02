package site.offload.api.member.dto.response;

import site.offload.api.quest.dto.response.CompleteQuestResponse;

import java.util.List;

public record VerifyPositionDistanceResponse(boolean isValidPosition, String successCharacterImageUrl,
                                             List<CompleteQuestResponse> completeQuestList) {

    public static VerifyPositionDistanceResponse of(boolean isValidPosition, String characterImageUrl, List<CompleteQuestResponse> completeQuestList) {
        return new VerifyPositionDistanceResponse(isValidPosition, characterImageUrl, completeQuestList);
    }
}
