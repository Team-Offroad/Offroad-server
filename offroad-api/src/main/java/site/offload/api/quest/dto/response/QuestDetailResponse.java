package site.offload.api.quest.dto.response;

import lombok.Builder;

@Builder
public record QuestDetailResponse(String questName, String description,
                                  int currentCount, int totalCount, String requirement,
                                  String reward) {

    public static QuestDetailResponse of(String questName, String description,
                                         int currentCount, int totalCount, String requirement,
                                         String reward) {
        return QuestDetailResponse.builder()
                .questName(questName)
                .currentCount(currentCount)
                .totalCount(totalCount)
                .requirement(requirement)
                .reward(reward)
                .description(description)
                .build();
    }
}
