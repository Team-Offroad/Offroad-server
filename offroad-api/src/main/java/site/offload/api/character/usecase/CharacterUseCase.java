package site.offload.api.character.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.character.dto.response.CharacterListResponse;
import site.offload.api.character.dto.response.CharacterResponse;
import site.offload.api.character.service.CharacterService;
import sites.offload.db.character.entity.CharacterEntity;
import sites.offload.external.aws.S3Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterUseCase {

    private final CharacterService characterService;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public CharacterListResponse getCharacters() {
        List<CharacterEntity> findCharacterEntities = characterService.findAll();
        List<CharacterResponse> charactersList = findCharacterEntities.stream().map(
                character -> CharacterResponse.builder()
                        .id(character.getId())
                        .description(character.getDescription())
                        .characterCode(character.getCharacterCode())
                        .name(character.getName())
                        .characterBaseImageUrl(s3Service.getPresignUrl(character.getCharacterSelectImageUrl()))
                        .build()
        ).toList();
        return CharacterListResponse.of(charactersList);
    }
}
