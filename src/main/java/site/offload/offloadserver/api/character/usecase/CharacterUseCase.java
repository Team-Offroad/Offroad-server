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
        final Iterable<Character> findCharacters = characterService.findAll();
        final List<CharacterResponse> charactersList = StreamSupport.stream(findCharacters.spliterator(), false)
                .map(character -> CharacterResponse.of(character.getId(), character.getDescription(),
                        character.getCharacterBaseImageUrl(), character.getCharacterCode()))
                .toList();
        return CharacterListResponse.of(charactersList);
    }
}
