package site.offload.api.character.usecase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.character.service.GainedCharacterService;
import site.offload.api.member.dto.response.GainedCharacterResponse;
import site.offload.dbjpa.character.entity.CharacterEntity;
import site.offload.dbjpa.character.repository.GainedCharacterRepository;
import site.offload.dbjpa.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static site.offload.api.fixture.CharacterEntityFixtureCreator.createCharacterEntity;

@ExtendWith(MockitoExtension.class)
public class GainedCharacterUseCaseTest {

    @InjectMocks
    GainedCharacterService gainedCharacterService;

    @Mock
    GainedCharacterRepository gainedCharacterRepository;

    @Test
    @DisplayName("보유 캐릭터와 미보유 캐릭터를 구별되게 저장할 수 있다")
    void saveDistinctGainedCharacterAndNotGainedCharacter() {
        //given

        CharacterEntity characterEntity1 = createCharacterEntity("이름1", "캐릭터 코드1",
                "탐험 성공 이미지1", "기본 이미지1", "선택 이미지1",
                "주목 이미지1", "QR 실패 이미지1", "미보유 썸네일 이미지1"
                , "설명1", "위치 인증 실패 이미지1");

        CharacterEntity characterEntity2 = createCharacterEntity("이름2", "캐릭터 코드2",
                "탐험 성공 이미지2", "기본 이미지2", "선택 이미지2",
                "주목 이미지2", "QR 실패 이미지2", "미보유 썸네일 이미지2"
                , "설명2", "위치 인증 실패 이미지2");

        MemberEntity memberEntity = MemberEntity.builder().name("이름1").email("이메일1").sub("소셜아이디1").socialPlatform(SocialPlatform.GOOGLE).build();

        List<CharacterEntity> characterEntities = new ArrayList<CharacterEntity>();
        characterEntities.add(characterEntity1);
        characterEntities.add(characterEntity2);

        BDDMockito.given(gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity1))
                .willReturn(true);
        BDDMockito.given(gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity2))
                .willReturn(false);

        //when

        List<GainedCharacterResponse> gainedCharacters = characterEntities.stream()
                .filter(characterEntity -> gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity))
                .map(characterEntity -> GainedCharacterResponse.of(characterEntity.getName(), characterEntity.getCharacterBaseImageUrl(), characterEntity.getDescription()))
                .collect(Collectors.toList());

        List<GainedCharacterResponse> notGainedCharacters = characterEntities.stream()
                .filter(characterEntity -> !gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity))
                .map(characterEntity -> GainedCharacterResponse.of(characterEntity.getName(), characterEntity.getNotGainedCharacterThumbnailImageUrl(), characterEntity.getDescription()))
                .collect(Collectors.toList());


        //then

        Assertions.assertThat(gainedCharacters).contains(GainedCharacterResponse.of("이름1", "기본 이미지1", "설명1"));
        Assertions.assertThat(notGainedCharacters).contains(GainedCharacterResponse.of("이름2", "미보유 썸네일 이미지2", "설명2"));

    }
}
