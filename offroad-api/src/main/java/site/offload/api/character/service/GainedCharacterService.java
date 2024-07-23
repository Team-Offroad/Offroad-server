package site.offload.api.character.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sites.offload.db.character.entity.Character;
import sites.offload.db.character.entity.GainedCharacter;
import sites.offload.db.character.repository.GainedCharacterRepository;
import sites.offload.db.member.entity.Member;

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
