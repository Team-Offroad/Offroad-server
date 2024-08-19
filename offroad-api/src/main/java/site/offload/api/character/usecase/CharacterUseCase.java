package site.offload.api.character.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.character.dto.response.CharacterDetailResponse;
import site.offload.api.character.dto.response.StartCharactersResponse;
import site.offload.api.character.dto.response.StartCharacterResponse;
import site.offload.api.character.service.CharacterService;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.external.aws.S3Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterUseCase {

    private final CharacterService characterService;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public StartCharactersResponse getCharacters() {
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
}
