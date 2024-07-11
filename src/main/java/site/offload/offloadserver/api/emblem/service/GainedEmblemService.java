package site.offload.offloadserver.api.emblem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.db.emblem.entity.Emblem;
import site.offload.offloadserver.db.emblem.repository.GainedEmblemRepository;
import site.offload.offloadserver.db.member.entity.Member;

@Component
@RequiredArgsConstructor
public class GainedEmblemService {

    private final GainedEmblemRepository gainedEmblemRepository;

    public boolean isExistsByMemberAndEmblem(Member member, Emblem emblem) {
        return gainedEmblemRepository.existsByMemberAndEmblemName(member, emblem);
    }
}
