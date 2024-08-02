package site.offload.api.quest.dto.response;

public record CompleteQuestResponse(String name) {

    public static CompleteQuestResponse of(String name) {
        return new CompleteQuestResponse(name);
    }
}
