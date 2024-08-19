package site.offload.db.emblem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.emblem.entity.GainedEmblemEntity;
import site.offload.db.member.entity.MemberEntity;

import java.util.List;

public interface GainedEmblemRepository extends JpaRepository<GainedEmblemEntity, Integer> {

    boolean existsByMemberEntityAndEmblemCode(MemberEntity memberEntity, String emblemCode);

    List<GainedEmblemEntity> findAllByMemberEntityId(Long memberId);

    void deleteAllByMemberEntityId(long memberId);

    GainedEmblemEntity findByMemberEntityIdAndEmblemCode(Long memberId, String EmblemCode);
}
