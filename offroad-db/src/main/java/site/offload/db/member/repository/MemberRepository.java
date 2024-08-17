package site.offload.db.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.MemberStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsBySub(String sub);

    Optional<MemberEntity> findBySub(String sub);

    boolean existsByNickName(String nickname);

    List<MemberEntity> findAllByMemberStatusAndInactiveSinceBefore(MemberStatus status, LocalDateTime dateTime);
}
