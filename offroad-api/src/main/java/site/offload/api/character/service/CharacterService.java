package site.offload.api.character.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.api.exception.NotFoundException;
import sites.offload.db.character.entity.CharacterEntity;
import sites.offload.db.character.repository.CharacterRepository;
import sites.offload.enums.ErrorMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterEntity findById(final Integer id) {
        return characterRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CHARACTER_NOTFOUND_EXCEPTION)
        );
    }

    public List<CharacterEntity> findAll() {
        return characterRepository.findAll();
    }

    public CharacterEntity findByName(final String name) {
        return characterRepository.findByName(name).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CHARACTER_NOTFOUND_EXCEPTION)
        );
    }
}
