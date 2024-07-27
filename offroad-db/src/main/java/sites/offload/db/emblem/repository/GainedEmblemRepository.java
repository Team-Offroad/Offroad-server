package sites.offload.db.emblem.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.emblem.entity.GainedEmblemEntity;
import sites.offload.db.member.entity.MemberEntity;

import java.util.List;

public interface GainedEmblemRepository extends CrudRepository<GainedEmblemEntity, Integer> {

    boolean existsByMemberAndEmblemCode(MemberEntity memberEntity, String emblemCode);

    List<GainedEmblemEntity> findAllByMemberId(Long memberId);
}
