package site.offload.api.character.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import site.offload.api.exception.NotFoundException;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.character.repository.CharacterRepository;
import site.offload.enums.response.ErrorMessage;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static site.offload.api.fixture.CharacterEntityFixtureCreator.createCharacterEntity;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    @InjectMocks
    CharacterService characterService;

    @Mock
    CharacterRepository characterRepository;

    @Test
    @DisplayName("캐릭터 ID에 해당하는 캐릭터 엔티티를 조회할 수 있다.")
    void getCharacterEntityByCharacterId() {
        //given
        CharacterEntity characterEntity = createCharacterEntity("이름", "캐릭터 코드",
                "탐험 성공 이미지", "기본 이미지", "선택 이미지",
                "주목 이미지", "QR 실패 이미지", "미보유 썸네일 이미지"
                , "설명", "위치 인증 실패 이미지", "캐릭터 메인 색깔 코드", "캐릭터 서브 색깔 코드", "캐릭터 요약 설명", "캐릭터 아이콘 이미지");
        BDDMockito.given(characterRepository.findById(1)).willReturn(Optional.ofNullable(characterEntity));
        //when
        CharacterEntity expectedCharacterEntity = characterService.findById(1);
        //then
        Assertions.assertThat(expectedCharacterEntity).isEqualTo(characterEntity);
    }
}
