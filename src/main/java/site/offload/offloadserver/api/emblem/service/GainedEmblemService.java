package site.offload.offloadserver.api.emblem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.db.emblem.entity.Emblem;
import site.offload.offloadserver.db.emblem.entity.GainedEmblem;
import site.offload.offloadserver.db.emblem.repository.GainedEmblemRepository;
import site.offload.offloadserver.db.member.entity.Member;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GainedEmblemService {

    private final GainedEmblemRepository gainedEmblemRepository;

    public boolean isExistsByMemberAndEmblemCode(Member member, String emblemCode) {
        return gainedEmblemRepository.existsByMemberAndEmblemCode(member, emblemCode);
    }

    public List<GainedEmblem> findAllByMemberId(Long memberId) {
        return gainedEmblemRepository.findAllByMemberId(memberId);
    }
}
