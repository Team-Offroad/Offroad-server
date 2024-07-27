package site.offload.api.charactermotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sites.offload.db.charactermotion.entity.CharacterMotionEntity;
import sites.offload.db.charactermotion.entity.GainedCharacterMotionEntity;
import sites.offload.db.charactermotion.repository.GainedCharacterMotionRepository;
import sites.offload.db.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class GainedCharacterMotionService {

    private final GainedCharacterMotionRepository gainedCharacterMotionRepository;

    public boolean isExistByCharacterMotionAndMember(CharacterMotionEntity characterMotionEntity, MemberEntity memberEntity) {
        return gainedCharacterMotionRepository.existsByCharacterMotionAndMember(characterMotionEntity, memberEntity);
    }

    public GainedCharacterMotionEntity save(MemberEntity memberEntity, CharacterMotionEntity characterMotionEntity) {
        return gainedCharacterMotionRepository.save(
                GainedCharacterMotionEntity.builder()
                        .member(memberEntity)
                        .characterMotion(characterMotionEntity)
                        .build());
    }
}
