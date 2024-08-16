package site.offload.api.member.dto.response;

public record UserInfoResponse(
        String nickname,
        String currentEmblem,
        long elapsedDay,
        long completedQuestCount,
        long visitedPlaceCount

) {

    public static UserInfoResponse of(
            String nickname,
            String currentEmblem,
            Long elapsedDay,
            Long completedQuestCount,
            Long visitedPlaceCount
    ) {
        return new UserInfoResponse(
                nickname,
                currentEmblem,
                elapsedDay,
                completedQuestCount,
                visitedPlaceCount
        );
    }
}
