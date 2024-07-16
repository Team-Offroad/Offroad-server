package site.offload.offloadserver.api.character.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.db.character.entity.Character;
import site.offload.offloadserver.db.character.entity.GainedCharacter;
import site.offload.offloadserver.db.character.repository.GainedCharacterRepository;
import site.offload.offloadserver.db.member.entity.Member;

@Component
@RequiredArgsConstructor
public class GainedCharacterService {

    private final GainedCharacterRepository gainedCharacterRepository;

    public void saveGainedCharacter(Member member, Character character) {
        gainedCharacterRepository.save(GainedCharacter.builder()
                .member(member)
                .character(character)
                .build());
    }

    public boolean isExistsGainedCharacterByMemberAndCharacter(Member member, Character character) {
        return gainedCharacterRepository.existsByCharacterAndMember(character, member);
    }
}
