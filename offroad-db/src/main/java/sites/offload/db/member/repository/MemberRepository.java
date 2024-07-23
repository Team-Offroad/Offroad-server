package sites.offload.db.member.repository;


import org.springframework.data.repository.CrudRepository;
import sites.offload.db.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
    boolean existsBySub(String sub);

    Optional<Member> findBySub(String sub);

    boolean existsByNickName(String nickname);

}
