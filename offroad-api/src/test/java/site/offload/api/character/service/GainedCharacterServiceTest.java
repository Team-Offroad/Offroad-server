package site.offload.api.character.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.character.repository.GainedCharacterRepository;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;

import static site.offload.api.fixture.CharacterEntityFixtureCreator.createCharacterEntity;

@ExtendWith(MockitoExtension.class)
public class GainedCharacterServiceTest {

    @InjectMocks
    GainedCharacterService gainedCharacterService;

    @Mock
    GainedCharacterRepository gainedCharacterRepository;


    @Test
    @DisplayName("보유 캐릭터와 미보유 캐릭터를 구별할 수 있다.")
    void distinctGainedCharacterAndNotGainedCharacter() {
        //given

        MemberEntity memberEntity = MemberEntity.builder().name("이름1").email("이메일1").sub("소셜아이디1").socialPlatform(SocialPlatform.GOOGLE).build();

        CharacterEntity characterEntity1 = createCharacterEntity("이름1", "캐릭터 코드1",
                "탐험 성공 이미지1", "기본 이미지1", "선택 이미지1",
                "주목 이미지1", "QR 실패 이미지1", "미보유 썸네일 이미지1"
                , "설명1", "위치 인증 실패 이미지1");

        CharacterEntity characterEntity2 = createCharacterEntity("이름2", "캐릭터 코드2",
                "탐험 성공 이미지2", "기본 이미지2", "선택 이미지2",
                "주목 이미지2", "QR 실패 이미지2", "미보유 썸네일 이미지2"
                , "설명2", "위치 인증 실패 이미지2");

        BDDMockito.given(gainedCharacterRepository.existsByCharacterEntityAndMemberEntity(characterEntity1, memberEntity))
                .willReturn(true);
        BDDMockito.given(gainedCharacterRepository.existsByCharacterEntityAndMemberEntity(characterEntity2, memberEntity))
                .willReturn(false);

        //when

        boolean isExistsGainedCharacter1 = gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity1);
        boolean isExistsGainedCharacter2 = gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity2);

        //then

        Assertions.assertThat(isExistsGainedCharacter1).isEqualTo(true);
        Assertions.assertThat(isExistsGainedCharacter2).isEqualTo(false);

    }
}
