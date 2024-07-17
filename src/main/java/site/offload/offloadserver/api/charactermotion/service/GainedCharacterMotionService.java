package site.offload.offloadserver.api.charactermotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.db.charactermotion.entity.CharacterMotion;
import site.offload.offloadserver.db.charactermotion.entity.GainedCharacterMotion;
import site.offload.offloadserver.db.charactermotion.repository.GainedCharacterMotionRepository;
import site.offload.offloadserver.db.member.entity.Member;

@Component
@RequiredArgsConstructor
public class GainedCharacterMotionService {

    private final GainedCharacterMotionRepository gainedCharacterMotionRepository;

    public boolean isExistByCharacterMotionAndMember(CharacterMotion characterMotion, Member member) {
        return gainedCharacterMotionRepository.existsByCharacterMotionAndMember(characterMotion, member);
    }

    public void save(Member member, CharacterMotion characterMotion) {
        gainedCharacterMotionRepository.save(
                GainedCharacterMotion.builder()
                        .member(member)
                        .characterMotion(characterMotion)
                        .build());
    }
}
