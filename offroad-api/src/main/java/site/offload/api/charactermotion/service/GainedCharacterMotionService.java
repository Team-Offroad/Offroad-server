package site.offload.api.charactermotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sites.offload.db.charactermotion.entity.CharacterMotion;
import sites.offload.db.charactermotion.entity.GainedCharacterMotion;
import sites.offload.db.charactermotion.repository.GainedCharacterMotionRepository;
import sites.offload.db.member.entity.Member;

@Component
@RequiredArgsConstructor
public class GainedCharacterMotionService {

    private final GainedCharacterMotionRepository gainedCharacterMotionRepository;

    public boolean isExistByCharacterMotionAndMember(CharacterMotion characterMotion, Member member) {
        return gainedCharacterMotionRepository.existsByCharacterMotionAndMember(characterMotion, member);
    }

    public GainedCharacterMotion save(Member member, CharacterMotion characterMotion) {
        return gainedCharacterMotionRepository.save(
                GainedCharacterMotion.builder()
                        .member(member)
                        .characterMotion(characterMotion)
                        .build());
    }
}
