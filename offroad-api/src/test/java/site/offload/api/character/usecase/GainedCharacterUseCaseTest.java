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
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.character.repository.GainedCharacterRepository;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;

import java.util.ArrayList;
import java.util.List;

import static site.offload.api.character.CreateCharacter.createCharacterEntity;

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
        List<GainedCharacterResponse> isGainedCharacters = new ArrayList<GainedCharacterResponse>();
        List<GainedCharacterResponse> isNotGainedCharacters = new ArrayList<GainedCharacterResponse>();

        CharacterEntity characterEntity1 = createCharacterEntity("이름1", "캐릭터 코드1",
                "탐험 성공 이미지1", "기본 이미지1", "선택 이미지1",
                "주목 이미지1", "QR 실패 이미지1", "미보유 썸네일 이미지1"
                , "설명1", "위치 인증 실패 이미지1");

        CharacterEntity characterEntity2 = createCharacterEntity("이름2", "캐릭터 코드2",
                "탐험 성공 이미지2", "기본 이미지2", "선택 이미지2",
                "주목 이미지2", "QR 실패 이미지2", "미보유 썸네일 이미지2"
                , "설명2", "위치 인증 실패 이미지2");

        MemberEntity memberEntity = MemberEntity.builder().name("이름1").email("이메일1").sub("소셜아이디1").socialPlatform(SocialPlatform.GOOGLE).build();

        BDDMockito.given(gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity1))
                .willReturn(true);
        BDDMockito.given(gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity2))
                .willReturn(false);

        //when

        if (gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity1)){
            isGainedCharacters.add(GainedCharacterResponse.of(characterEntity1.getName(), characterEntity1.getCharacterBaseImageUrl(), characterEntity1.getDescription()));
        }
        else{
            isNotGainedCharacters.add(GainedCharacterResponse.of(characterEntity1.getName(), characterEntity1.getNotGainedCharacterThumbnailImageUrl(), characterEntity1.getDescription()));
        }

        if (gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity2)){
            isGainedCharacters.add(GainedCharacterResponse.of(characterEntity2.getName(), characterEntity2.getCharacterBaseImageUrl(), characterEntity2.getDescription()));
        }
        else{
            isNotGainedCharacters.add(GainedCharacterResponse.of(characterEntity2.getName(), characterEntity2.getNotGainedCharacterThumbnailImageUrl(), characterEntity2.getDescription()));
        }

        //then

        Assertions.assertThat(isGainedCharacters).contains(GainedCharacterResponse.of("이름1", "기본 이미지1", "설명1"));
        Assertions.assertThat(isNotGainedCharacters).contains(GainedCharacterResponse.of("이름2", "미보유 썸네일 이미지2", "설명2"));

    }
}
