package site.offload.api.emblem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.db.emblem.entity.GainedEmblemEntity;
import site.offload.db.emblem.repository.GainedEmblemRepository;
import site.offload.db.member.entity.MemberEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GainedEmblemService {

    private final GainedEmblemRepository gainedEmblemRepository;

    public boolean isExistsByMemberAndEmblemCode(MemberEntity memberEntity, String emblemCode) {
        return gainedEmblemRepository.existsByMemberEntityAndEmblemCode(memberEntity, emblemCode);
    }

    public List<GainedEmblemEntity> findAllByMemberId(Long memberId) {
        return gainedEmblemRepository.findAllByMemberEntityId(memberId);
    }

    public Integer save(MemberEntity memberEntity, String emblemCode) {
        return gainedEmblemRepository.save(GainedEmblemEntity.create(memberEntity, emblemCode)).getId();
    }

    public void deleteAllByMemberId(long memberId) {
        gainedEmblemRepository.deleteAllByMemberEntityId(memberId);
    }

    public GainedEmblemEntity findByMemberIdAndEmblemCode(Long memberId, String emblemCode) {
        return gainedEmblemRepository.findByMemberEntityIdAndEmblemCode(memberId, emblemCode);
    }
}
