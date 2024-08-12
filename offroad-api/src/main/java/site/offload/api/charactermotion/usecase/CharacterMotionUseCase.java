package site.offload.api.charactermotion.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.character.service.CharacterService;
import site.offload.api.charactermotion.dto.CharacterMotionResponse;
import site.offload.api.charactermotion.dto.CharacterMotionsResponse;
import site.offload.api.charactermotion.service.CharacterMotionService;
import site.offload.api.charactermotion.service.GainedCharacterMotionService;
import site.offload.api.member.service.MemberService;
import site.offload.dbjpa.character.entity.CharacterEntity;
import site.offload.dbjpa.charactermotion.entity.CharacterMotionEntity;
import site.offload.dbjpa.member.entity.MemberEntity;
import site.offload.external.aws.S3Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterMotionUseCase {

    private final CharacterMotionService characterMotionService;
    private final GainedCharacterMotionService gainedCharacterMotionService;
    private final CharacterService characterService;
    private final MemberService memberService;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public CharacterMotionsResponse getMotions(Long memberId, Integer characterId) {
        MemberEntity findMemberEntity = memberService.findById(memberId);
        CharacterEntity findCharacterEntity = characterService.findById(characterId);
        List<CharacterMotionEntity> characterMotionEntities = characterMotionService.findCharacterMotionsByCharacterEntity(findCharacterEntity);

        List<CharacterMotionResponse> gainedCharacterMotions = characterMotionEntities.stream()
                .filter(characterMotionEntity -> gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotionEntity, findMemberEntity))
                .map(characterMotionEntity -> CharacterMotionResponse.of(characterMotionEntity.getPlaceCategory().toString(), s3Service.getPresignUrl(characterMotionEntity.getMotionCaptureImageUrl())))
                .toList();

        List<CharacterMotionResponse> notGainedCharacterMotions = characterMotionEntities.stream()
                .filter(characterMotionEntity -> !gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotionEntity, findMemberEntity))
                .map(characterMotionEntity -> CharacterMotionResponse.of(characterMotionEntity.getPlaceCategory().toString(), s3Service.getPresignUrl(characterMotionEntity.getNotGainedMotionThumbnailImageUrl())))
                .toList();

        return CharacterMotionsResponse.of(gainedCharacterMotions, notGainedCharacterMotions);
    }
}
