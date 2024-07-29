package site.offload.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.api.exception.NotFoundException;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.member.repository.MemberRepository;
import site.offload.enums.response.ErrorMessage;

@Component
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberEntity findById(final Long id) {
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

    public MemberEntity findBySub(String sub) {
        return memberRepository.findBySub(sub).orElseThrow(
                () -> new NotFoundException(ErrorMessage.MEMBER_NOTFOUND_EXCEPTION)
        );
    }

    public void saveMember(MemberEntity memberEntity) {
        memberRepository.save(memberEntity);
    }
}
