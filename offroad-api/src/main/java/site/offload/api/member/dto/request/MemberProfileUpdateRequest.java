package site.offload.api.member.dto.request;


import site.offload.enums.member.MemberGender;

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
