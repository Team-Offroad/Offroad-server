package site.offload.offloadserver.api.character.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.db.character.entity.Character;
import site.offload.offloadserver.db.character.repository.CharacterRepository;

@Component
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;

    public Character findById(Integer id) {
        return characterRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.CHARACTER_NOTFOUND_EXCEPTION)
        );
    }
}
