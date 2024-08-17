package site.offload.api.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.member.repository.MemberRepository;
import site.offload.enums.member.MemberStatus;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceBatch {

    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void removeInactiveMembers() {
        final LocalDateTime fiveYearsAgo = LocalDateTime.now().minusYears(5);
        final List<MemberEntity> membersToDelete = memberRepository.findAllByMemberStatusAndInactiveSinceBefore(MemberStatus.INACTIVE, fiveYearsAgo);
        memberRepository.deleteAll(membersToDelete);
    }
}
