package site.offload.dbjpa.emblem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import site.offload.dbjpa.emblem.entity.GainedEmblemEntity;
import site.offload.dbjpa.member.entity.MemberEntity;

import java.util.List;

public interface GainedEmblemRepository extends JpaRepository<GainedEmblemEntity, Integer> {

    boolean existsByMemberEntityAndEmblemCode(MemberEntity memberEntity, String emblemCode);

    List<GainedEmblemEntity> findAllByMemberEntityId(Long memberId);

    void deleteAllByMemberEntityId(long memberId);
}
