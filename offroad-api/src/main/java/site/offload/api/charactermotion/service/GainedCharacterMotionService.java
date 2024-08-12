package site.offload.api.charactermotion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.dbjpa.charactermotion.entity.CharacterMotionEntity;
import site.offload.dbjpa.charactermotion.entity.GainedCharacterMotionEntity;
import site.offload.dbjpa.charactermotion.repository.GainedCharacterMotionRepository;
import site.offload.dbjpa.member.entity.MemberEntity;

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
}
