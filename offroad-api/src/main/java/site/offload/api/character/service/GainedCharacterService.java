package site.offload.api.character.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.dbjpa.character.entity.CharacterEntity;
import site.offload.dbjpa.character.entity.GainedCharacterEntity;
import site.offload.dbjpa.character.repository.GainedCharacterRepository;
import site.offload.dbjpa.member.entity.MemberEntity;

import java.util.List;

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
