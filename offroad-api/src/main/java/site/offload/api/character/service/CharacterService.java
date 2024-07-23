package site.offload.api.character.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.api.exception.NotFoundException;
import sites.offload.db.character.entity.Character;
import sites.offload.db.character.repository.CharacterRepository;
import sites.offload.enums.ErrorMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;

    public Character findById(final Integer id) {
        return characterRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CHARACTER_NOTFOUND_EXCEPTION)
        );
    }

    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    public Character findByName(final String name) {
        return characterRepository.findByName(name).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CHARACTER_NOTFOUND_EXCEPTION)
        );
    }
}
