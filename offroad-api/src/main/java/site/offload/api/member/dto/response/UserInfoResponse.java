package site.offload.api.member.dto.response;

public record UserInfoResponse(
        String nickname,
        String currentEmblem,
        long elapsedDay,
        long completeQuestCount,
        long visitedPlaceCount

) {

    public static UserInfoResponse of(
            String nickname,
            String currentEmblem,
            Long elapsedDay,
            Long completeQuestCount,
            Long visitedPlaceCount
    ) {
        return new UserInfoResponse(
                nickname,
                currentEmblem,
                elapsedDay,
                completeQuestCount,
                visitedPlaceCount
        );
    }
}
