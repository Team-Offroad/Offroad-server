package site.offload.offloadserver.api.member.service.validator;

import site.offload.offloadserver.db.member.entity.Member;

import java.util.Optional;

public class MemberValidator {

    public static boolean isNewMember(Optional<Member> member) {
        return member.isEmpty();
    }
}
