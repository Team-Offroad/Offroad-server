package site.offload.api.charactermotion.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.character.service.GainedCharacterService;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.charactermotion.entity.CharacterMotionEntity;
import site.offload.db.charactermotion.entity.GainedCharacterMotionEntity;
import site.offload.db.charactermotion.repository.GainedCharacterMotionRepository;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;
import site.offload.enums.place.PlaceCategory;

import static org.mockito.ArgumentMatchers.any;
import static site.offload.api.fixture.CharacterEntityFixtureCreator.createCharacterEntity;
import static site.offload.api.fixture.CharacterMotionEntityFixtureCreator.createCharacterMotionEntity;
import static site.offload.api.fixture.GainedCharacterMotionEntityFixtureCreator.createGainedCharacterMotionEntity;

@ExtendWith(MockitoExtension.class)
public class GainedCharacterMotionServiceTest {

    @InjectMocks
    GainedCharacterMotionService gainedCharacterMotionService;

    @Mock
    GainedCharacterMotionRepository gainedCharacterMotionRepository;

    @Test
    @DisplayName("멤버가 해당 캐릭터 모션을 얻었는지 확인할 수 있다.")
    void checkMemberGetCharacterMotion() {
        //given
        MemberEntity memberEntity = MemberEntity.builder().name("이름1").email("이메일1").sub("소셜아이디1").socialPlatform(SocialPlatform.GOOGLE).build();
        CharacterEntity characterEntity = createCharacterEntity("이름", "캐릭터 코드",
                "탐험 성공 이미지", "기본 이미지", "선택 이미지",
                "주목 이미지", "QR 실패 이미지", "미보유 썸네일 이미지"
                , "설명", "위치 인증 실패 이미지", "캐릭터 메인 색깔 코드", "캐릭터 서브 색깔 코드", "캐릭터 요약 설명", "캐릭터 아이콘 이미지");
        CharacterMotionEntity characterMotionEntity = createCharacterMotionEntity(characterEntity, PlaceCategory.CAFFE, "모션 이미지1", "미보유 이미지1", "캡쳐 이미지1");
        GainedCharacterMotionEntity gainedCharacterMotionEntity = createGainedCharacterMotionEntity(memberEntity, characterMotionEntity);
        BDDMockito.given(gainedCharacterMotionRepository.findByMemberEntityAndCharacterMotionEntity(any(), any())).willReturn(gainedCharacterMotionEntity);


        //when

        GainedCharacterMotionEntity expectedGainedCharacterMotionEntity = gainedCharacterMotionService.findByMemberEntityAndCharacterMotionEntity(memberEntity, characterMotionEntity);

        //then

        Assertions.assertThat(expectedGainedCharacterMotionEntity).isEqualTo(gainedCharacterMotionEntity);
    }
}
