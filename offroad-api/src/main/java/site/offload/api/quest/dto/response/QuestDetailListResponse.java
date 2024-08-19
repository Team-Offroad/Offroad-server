package site.offload.api.quest.dto.response;

import java.util.List;

public record QuestDetailListResponse(List<QuestDetailResponse> questList) {
    public static QuestDetailListResponse of(List<QuestDetailResponse> questList) {
        return new QuestDetailListResponse(questList);
    }
}
