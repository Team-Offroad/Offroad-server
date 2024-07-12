package site.offload.offloadserver.api.charactermotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.db.character.repository.GainedCharacterRepository;
import site.offload.offloadserver.db.charactermotion.entity.CharacterMotion;
import site.offload.offloadserver.db.member.entity.Member;

@Component
@RequiredArgsConstructor
public class GainedCharacterMotionService {

    private final GainedCharacterRepository gainedCharacterRepository;

    public boolean isExistByCharacterMotionAndMember(CharacterMotion characterMotion, Member member) {
        return gainedCharacterRepository.existsByCharacterAndMember(characterMotion, member);
    }
}
