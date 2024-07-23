package sites.offload.db.emblem.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.emblem.entity.GainedEmblem;
import sites.offload.db.member.entity.Member;

import java.util.List;

public interface GainedEmblemRepository extends CrudRepository<GainedEmblem, Integer> {

    boolean existsByMemberAndEmblemCode(Member member, String emblemCode);

    List<GainedEmblem> findAllByMemberId(Long memberId);
}
