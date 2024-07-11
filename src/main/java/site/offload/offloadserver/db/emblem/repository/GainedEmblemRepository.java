package site.offload.offloadserver.db.emblem.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.emblem.entity.Emblem;
import site.offload.offloadserver.db.emblem.entity.GainedEmblem;
import site.offload.offloadserver.db.member.entity.Member;

import java.util.List;

public interface GainedEmblemRepository extends CrudRepository<GainedEmblem, Integer> {

    boolean existsByMemberAndEmblemName(Member member, Emblem emblem);

    List<GainedEmblem> findAllByMemberId(Long memberId);
}
