package site.offload.db.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.member.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsBySub(String sub);

    Optional<MemberEntity> findBySub(String sub);

    boolean existsByNickName(String nickname);

}
