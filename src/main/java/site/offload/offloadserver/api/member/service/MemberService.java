package site.offload.offloadserver.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.db.member.entity.Member;
import site.offload.offloadserver.db.member.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOTFOUND_EXCEPTION)
        );
    }
}
