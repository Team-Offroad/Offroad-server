package site.offload.offloadserver.api.character.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.character.dto.response.CharacterListResponse;
import site.offload.offloadserver.api.character.dto.response.CharacterResponse;
import site.offload.offloadserver.api.character.service.CharacterService;
import site.offload.offloadserver.db.character.entity.Character;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CharacterUseCase {

    private final CharacterService characterService;

    @Transactional(readOnly = true)
    public CharacterListResponse getCharacters() {
        List<Character> findCharacters = characterService.findAll();
        List<CharacterResponse> charactersList = findCharacters.stream().map(
                character -> CharacterResponse.builder()
                        .id(character.getId())
                        .description(character.getDescription())
                        .characterCode(character.getCharacterCode())
                        .characterBaseImageUrl(character.getCharacterBaseImageUrl())
                        .name(character.getName())
                        .build()
        ).toList();
        return CharacterListResponse.of(charactersList);
    }
}
