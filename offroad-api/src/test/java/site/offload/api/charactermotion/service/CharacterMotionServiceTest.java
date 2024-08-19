package site.offload.api.charactermotion.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.charactermotion.entity.CharacterMotionEntity;
import site.offload.db.charactermotion.entity.GainedCharacterMotionEntity;
import site.offload.db.charactermotion.repository.CharacterMotionRepository;
import site.offload.db.charactermotion.repository.GainedCharacterMotionRepository;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;
import site.offload.enums.place.PlaceCategory;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static site.offload.api.fixture.CharacterEntityFixtureCreator.createCharacterEntity;
import static site.offload.api.fixture.CharacterMotionEntityFixtureCreator.createCharacterMotionEntity;
import static site.offload.api.fixture.GainedCharacterMotionEntityFixtureCreator.createGainedCharacterMotionEntity;

@ExtendWith(MockitoExtension.class)
public class CharacterMotionServiceTest {

    @InjectMocks
    CharacterMotionService characterMotionService;

    @Mock
    CharacterMotionRepository characterMotionRepository;

    @Test
    @DisplayName("해당 캐릭터에 맞는 캐릭터 모션들을 불러올 수 있다.")
    void getCharacterMotionsByCharacterEntity() {

        //given
        CharacterEntity characterEntity = createCharacterEntity("이름", "캐릭터 코드",
                "탐험 성공 이미지", "기본 이미지", "선택 이미지",
                "주목 이미지", "QR 실패 이미지", "미보유 썸네일 이미지"
                , "설명", "위치 인증 실패 이미지", "캐릭터 메인 색깔 코드", "캐릭터 서브 색깔 코드", "캐릭터 요약 설명", "캐릭터 아이콘 이미지");

        CharacterMotionEntity characterMotionEntity1 = createCharacterMotionEntity(characterEntity, PlaceCategory.CAFFE, "모션 이미지1", "미보유 이미지1", "캡쳐 이미지1");
        CharacterMotionEntity characterMotionEntity2 = createCharacterMotionEntity(characterEntity, PlaceCategory.CULTURE, "모션 이미지2", "미보유 이미지2", "캡쳐 이미지2");
        CharacterMotionEntity characterMotionEntity3 = createCharacterMotionEntity(characterEntity, PlaceCategory.RESTAURANT, "모션 이미지3", "미보유 이미지3", "캡쳐 이미지3");
        CharacterMotionEntity characterMotionEntity4 = createCharacterMotionEntity(characterEntity, PlaceCategory.SPORT, "모션 이미지4", "미보유 이미지4", "캡쳐 이미지4");

        List<CharacterMotionEntity> characterMotionEntities = new ArrayList<CharacterMotionEntity>();
        characterMotionEntities.add(characterMotionEntity1);
        characterMotionEntities.add(characterMotionEntity2);
        characterMotionEntities.add(characterMotionEntity3);
        characterMotionEntities.add(characterMotionEntity4);


        BDDMockito.given(characterMotionRepository.findAllByCharacterEntity(any())).willReturn(characterMotionEntities);

        //when

        List<CharacterMotionEntity> expectedResult = characterMotionService.findCharacterMotionsByCharacterEntity(characterEntity);

        //then

        Assertions.assertThat(expectedResult).isEqualTo(characterMotionEntities);
    }
}
