package site.offload.api.character.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.character.entity.GainedCharacterEntity;
import site.offload.db.character.repository.GainedCharacterRepository;
import site.offload.db.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class GainedCharacterService {

    private final GainedCharacterRepository gainedCharacterRepository;

    public void saveGainedCharacter(MemberEntity memberEntity, CharacterEntity characterEntity) {
        gainedCharacterRepository.save(GainedCharacterEntity.builder()
                .memberEntity(memberEntity)
                .characterEntity(characterEntity)
                .build());
    }

    public boolean isExistsGainedCharacterByMemberAndCharacter(MemberEntity memberEntity, CharacterEntity characterEntity) {
        return gainedCharacterRepository.existsByCharacterEntityAndMemberEntity(characterEntity, memberEntity);
    }

    public void deleteAllByMemberId(long memberId) {
        gainedCharacterRepository.deleteAllByMemberEntityId(memberId);
    }
}
