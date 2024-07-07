package site.offload.offloadserver.db.repository;

import org.springframework.data.repository.CrudRepository;
import site.offload.offloadserver.db.entity.Member;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
