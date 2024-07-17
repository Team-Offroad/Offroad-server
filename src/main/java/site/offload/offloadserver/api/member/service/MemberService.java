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

    public Member findById(final Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOTFOUND_EXCEPTION)
        );
    }

    public boolean isExistsByNickname(final String nickname) {
        return memberRepository.existsByNickName(nickname);
    }

    public boolean isExistsBySub(String sub) {
        return memberRepository.existsBySub(sub);
    }

    public Member findBySub(String sub) {
        return memberRepository.findBySub(sub).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOTFOUND_EXCEPTION)
        );
    }

    public void saveMember(Member member){
        memberRepository.save(member);
    }
}
