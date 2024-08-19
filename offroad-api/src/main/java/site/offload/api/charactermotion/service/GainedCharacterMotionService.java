package site.offload.api.charactermotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.db.charactermotion.entity.CharacterMotionEntity;
import site.offload.db.charactermotion.entity.GainedCharacterMotionEntity;
import site.offload.db.charactermotion.repository.GainedCharacterMotionRepository;
import site.offload.db.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class GainedCharacterMotionService {

    private final GainedCharacterMotionRepository gainedCharacterMotionRepository;

    public boolean isExistByCharacterMotionAndMember(CharacterMotionEntity characterMotionEntity, MemberEntity memberEntity) {
        return gainedCharacterMotionRepository.existsByCharacterMotionEntityAndMemberEntity(characterMotionEntity, memberEntity);
    }

    public GainedCharacterMotionEntity save(MemberEntity memberEntity, CharacterMotionEntity characterMotionEntity) {
        return gainedCharacterMotionRepository.save(
                GainedCharacterMotionEntity.builder()
                        .memberEntity(memberEntity)
                        .characterMotionEntity(characterMotionEntity)
                        .build());
    }

    public void deleteAllByMemberId(long memberId) {
        gainedCharacterMotionRepository.deleteAllByMemberEntityId(memberId);
    }

    public GainedCharacterMotionEntity findByMemberEntityAndCharacterMotionEntity(MemberEntity memberEntity, CharacterMotionEntity characterMotionEntity) {
        return gainedCharacterMotionRepository.findByMemberEntityAndCharacterMotionEntity(memberEntity, characterMotionEntity);
    }
}
