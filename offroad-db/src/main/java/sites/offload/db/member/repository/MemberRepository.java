package sites.offload.db.member.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.member.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<MemberEntity, Long> {
    boolean existsBySub(String sub);

    Optional<MemberEntity> findBySub(String sub);

    boolean existsByNickName(String nickname);

}
