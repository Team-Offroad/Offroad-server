package site.offload.api.emblem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sites.offload.db.emblem.entity.GainedEmblem;
import sites.offload.db.emblem.repository.GainedEmblemRepository;
import sites.offload.db.member.entity.Member;

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

    public Integer save(Member member, String emblemCode) {
        return gainedEmblemRepository.save(GainedEmblem.create(member, emblemCode)).getId();
    }
}
