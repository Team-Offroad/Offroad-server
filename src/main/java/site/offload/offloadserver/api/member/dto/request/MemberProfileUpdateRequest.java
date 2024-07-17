package site.offload.offloadserver.api.member.dto.request;

import site.offload.offloadserver.db.member.entity.MemberGender;

public record MemberProfileUpdateRequest(
        String nickname,
        Integer year,
        Integer month,
        Integer day,
        MemberGender gender
) {
    public static MemberProfileUpdateRequest of(String nickname, Integer year, Integer month, Integer day, MemberGender gender) {
        return new MemberProfileUpdateRequest(nickname, year, month, day, gender);
    }
}
