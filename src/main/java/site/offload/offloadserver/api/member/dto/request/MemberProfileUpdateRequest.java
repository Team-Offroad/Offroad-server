package site.offload.offloadserver.api.member.dto.request;

import site.offload.offloadserver.db.member.entity.MemberGender;

public record MemberProfileUpdateRequest(
        String nickName,
        int year,
        int month,
        int day,
        MemberGender gender
) {
    public static MemberProfileUpdateRequest of(String nickName, int year, int month, int day, MemberGender gender) {
        return new MemberProfileUpdateRequest(nickName, year, month, day, gender);
    }
}
