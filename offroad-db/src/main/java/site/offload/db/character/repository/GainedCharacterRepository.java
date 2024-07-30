package site.offload.db.character.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.character.entity.GainedCharacterEntity;
import site.offload.db.member.entity.MemberEntity;

public interface GainedCharacterRepository extends JpaRepository<GainedCharacterEntity, Long> {

    boolean existsByCharacterEntityAndMemberEntity(CharacterEntity characterEntity, MemberEntity memberEntity);

    void deleteAllByMemberEntityId(long memberId);

}
