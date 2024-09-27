package site.offload.api.character.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.character.dto.response.CharacterDetailResponse;
import site.offload.api.character.dto.response.StartCharactersResponse;
import site.offload.api.character.dto.response.StartCharacterResponse;
import site.offload.api.character.service.CharacterService;
import site.offload.api.character.service.GainedCharacterService;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.member.dto.response.ChooseCharacterResponse;
import site.offload.api.member.dto.response.GainedCharacterResponse;
import site.offload.api.member.dto.response.GainedCharactersResponse;
import site.offload.api.member.service.MemberService;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.character.entity.GainedCharacterEntity;
import site.offload.db.member.entity.MemberEntity;
import site.offload.external.aws.S3Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterUseCase {

    private final CharacterService characterService;
    private final S3Service s3Service;
    private final MemberService memberService;
    private final GainedCharacterService gainedCharacterService;
    private final GainedEmblemService gainedEmblemService;

    @Transactional(readOnly = true)
    public StartCharactersResponse getStartCharacters() {
        List<CharacterEntity> findCharacterEntities = characterService.findAll();
        List<StartCharacterResponse> charactersList = findCharacterEntities.stream().map(
                character -> StartCharacterResponse.builder()
                        .id(character.getId())
                        .description(character.getDescription())
                        .characterCode(character.getCharacterCode())
                        .name(character.getName())
                        .characterBaseImageUrl(s3Service.getPresignUrl(character.getCharacterSelectImageUrl()))
                        .build()
        ).toList();
        return StartCharactersResponse.of(charactersList);
    }

    @Transactional(readOnly = true)
    public CharacterDetailResponse getCharacterDetail(Integer characterId) {
        CharacterEntity characterEntity = characterService.findById(characterId);
        return CharacterDetailResponse.of(characterId, characterEntity.getName(), s3Service.getPresignUrl(characterEntity.getCharacterBaseImageUrl()), s3Service.getPresignUrl(characterEntity.getCharacterIconImageUrl()), characterEntity.getDescription(), characterEntity.getSummaryDescription(), characterEntity.getCharacterMainColorCode(), characterEntity.getCharacterSubColorCode());
    }

    @Transactional
    public ChooseCharacterResponse chooseCharacter(Long memberId, Integer characterId) {
        final MemberEntity findMemberEntity = memberService.findById(memberId);
        final CharacterEntity findCharacterEntity = characterService.findById(characterId);
        findMemberEntity.chooseCharacter(findCharacterEntity.getName());
        if (!gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(findMemberEntity, findCharacterEntity)) {
            gainedCharacterService.saveGainedCharacter(findMemberEntity, findCharacterEntity);
            gainedEmblemService.save(findMemberEntity, "TT000009");
        }
        return ChooseCharacterResponse.of(s3Service.getPresignUrl(findCharacterEntity.getCharacterSpotLightImageUrl()));
    }

    @Transactional
    public GainedCharactersResponse getCharacters(Long memberId) {
        List<CharacterEntity> characterEntities = characterService.findAll();
        MemberEntity memberEntity = memberService.findById(memberId);
        CharacterEntity currentCharacterEntity = characterService.findByName(memberEntity.getCurrentCharacterName());


        List<GainedCharacterResponse> gainedCharacters = characterEntities.stream()
                .filter(characterEntity -> gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity))
                .map(characterEntity -> {
                    GainedCharacterEntity gainedCharacterEntity = gainedCharacterService.findByMemberEntityAndCharacterEntity(memberEntity, characterEntity);
                    boolean isNewGained = gainedCharacterEntity.isNewGained();
                    gainedCharacterEntity.updateNewGainedStatus();
                    return GainedCharacterResponse.of(characterEntity.getId(), characterEntity.getName(), s3Service.getPresignUrl(characterEntity.getCharacterBaseImageUrl()), characterEntity.getCharacterMainColorCode(), characterEntity.getCharacterSubColorCode(), isNewGained);
                })
                .collect(Collectors.toList());

        List<GainedCharacterResponse> notGainedCharacters = characterEntities.stream()
                .filter(characterEntity -> !gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity))
                .map(characterEntity -> GainedCharacterResponse.of(
                        characterEntity.getId(),
                        characterEntity.getName(),
                        s3Service.getPresignUrl(characterEntity.getNotGainedCharacterThumbnailImageUrl()),
                        characterEntity.getCharacterMainColorCode(),
                        characterEntity.getCharacterSubColorCode(),
                        false)
                )
                .collect(Collectors.toList());

        return GainedCharactersResponse.of(gainedCharacters, notGainedCharacters, currentCharacterEntity.getId());
    }
}
