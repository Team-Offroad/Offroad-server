package site.offload.dbjpa.character.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.dbjpa.character.entity.CharacterEntity;
import site.offload.dbjpa.character.entity.GainedCharacterEntity;
import site.offload.dbjpa.member.entity.MemberEntity;

import java.util.List;

public interface GainedCharacterRepository extends JpaRepository<GainedCharacterEntity, Long> {

    boolean existsByCharacterEntityAndMemberEntity(CharacterEntity characterEntity, MemberEntity memberEntity);

    void deleteAllByMemberEntityId(long memberId);

}
