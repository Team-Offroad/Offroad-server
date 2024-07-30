package site.offload.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.db.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class TestMemberService {

    private final MemberRepository memberRepository;

    public void deleteTestMember(long id) {
        memberRepository.deleteById(id);
    }

}
