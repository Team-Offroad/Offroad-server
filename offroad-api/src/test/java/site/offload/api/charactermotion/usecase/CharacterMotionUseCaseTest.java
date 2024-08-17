package site.offload.api.charactermotion.usecase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.character.service.CharacterService;
import site.offload.api.charactermotion.dto.CharacterMotionResponse;
import site.offload.api.charactermotion.dto.CharacterMotionsResponse;
import site.offload.api.charactermotion.service.CharacterMotionService;
import site.offload.api.charactermotion.service.GainedCharacterMotionService;
import site.offload.api.fixture.GainedCharacterMotionEntityFixtureCreator;
import site.offload.api.member.service.MemberService;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.charactermotion.entity.CharacterMotionEntity;
import site.offload.db.charactermotion.entity.GainedCharacterMotionEntity;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.member.SocialPlatform;
import site.offload.enums.place.PlaceCategory;
import site.offload.external.aws.S3Service;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static site.offload.api.fixture.CharacterEntityFixtureCreator.createCharacterEntity;
import static site.offload.api.fixture.CharacterMotionEntityFixtureCreator.createCharacterMotionEntity;

@ExtendWith(MockitoExtension.class)
public class CharacterMotionUseCaseTest {

    @InjectMocks
    CharacterMotionUseCase characterMotionUseCase;

    @Mock
    MemberService memberService;

    @Mock
    CharacterService characterService;

    @Mock
    CharacterMotionService characterMotionService;

    @Mock
    GainedCharacterMotionService gainedCharacterMotionService;

    @Mock
    S3Service s3Service;


    @Test
    @DisplayName("보유 및 미보유 모션을 구별할 수 있다.")
    void distinctGainedAndNotGainedMotion() {
        //given
        MemberEntity memberEntity = MemberEntity.builder().name("이름1").email("이메일1").sub("소셜아이디1").socialPlatform(SocialPlatform.GOOGLE).build();
        CharacterEntity characterEntity = createCharacterEntity("이름1", "캐릭터 코드1",
                "탐험 성공 이미지1", "기본 이미지1", "선택 이미지1",
                "주목 이미지1", "QR 실패 이미지1", "미보유 썸네일 이미지1"
                , "설명1", "위치 인증 실패 이미지1", "캐릭터 메인 색깔 코드1", "캐릭터 서브 색깔 코드1", "캐릭터 요약 설명1", "캐릭터 아이콘 이미지1");

        CharacterMotionEntity characterMotionEntity1 = createCharacterMotionEntity(characterEntity, PlaceCategory.CAFFE, "모션 이미지1", "미보유 이미지1", "모션 캡쳐 이미지1");
        CharacterMotionEntity characterMotionEntity2 = createCharacterMotionEntity(characterEntity, PlaceCategory.CULTURE, "모션 이미지2", "미보유 이미지2", "모션 캡쳐 이미지2");
        CharacterMotionEntity characterMotionEntity3 = createCharacterMotionEntity(characterEntity, PlaceCategory.RESTAURANT, "모션 이미지3", "미보유 이미지3", "모션 캡쳐 이미지3");
        CharacterMotionEntity characterMotionEntity4 = createCharacterMotionEntity(characterEntity, PlaceCategory.SPORT, "모션 이미지4", "미보유 이미지4", "모션 캡쳐 이미지4");

        GainedCharacterMotionEntity gainedCharacterMotionEntity1 = GainedCharacterMotionEntityFixtureCreator.createGainedCharacterMotionEntity(memberEntity, characterMotionEntity1);
        GainedCharacterMotionEntity gainedCharacterMotionEntity2 = GainedCharacterMotionEntityFixtureCreator.createGainedCharacterMotionEntity(memberEntity, characterMotionEntity2);

        List<CharacterMotionEntity> characterMotionEntities = new ArrayList<CharacterMotionEntity>();
        characterMotionEntities.add(characterMotionEntity1);
        characterMotionEntities.add(characterMotionEntity2);
        characterMotionEntities.add(characterMotionEntity3);
        characterMotionEntities.add(characterMotionEntity4);

        BDDMockito.given(memberService.findById(any())).willReturn(memberEntity);
        BDDMockito.given(characterService.findById(any())).willReturn(characterEntity);
        BDDMockito.given(characterMotionService.findCharacterMotionsByCharacterEntity(any())).willReturn(characterMotionEntities);
        BDDMockito.given(gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotionEntity1, memberEntity)).willReturn(true);
        BDDMockito.given(gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotionEntity2, memberEntity)).willReturn(true);
        BDDMockito.given(gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotionEntity3, memberEntity)).willReturn(false);
        BDDMockito.given(gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotionEntity4, memberEntity)).willReturn(false);
        BDDMockito.given(s3Service.getPresignUrl(characterMotionEntity1.getMotionCaptureImageUrl())).willReturn("모션 캡쳐 이미지1");
        BDDMockito.given(s3Service.getPresignUrl(characterMotionEntity2.getMotionCaptureImageUrl())).willReturn("모션 캡쳐 이미지2");
        BDDMockito.given(s3Service.getPresignUrl(characterMotionEntity3.getNotGainedMotionThumbnailImageUrl())).willReturn("미보유 이미지3");
        BDDMockito.given(s3Service.getPresignUrl(characterMotionEntity4.getNotGainedMotionThumbnailImageUrl())).willReturn("미보유 이미지4");
        BDDMockito.given(gainedCharacterMotionService.findByMemberEntityAndCharacterMotionEntity(memberEntity, characterMotionEntity1)).willReturn(gainedCharacterMotionEntity1);
        BDDMockito.given(gainedCharacterMotionService.findByMemberEntityAndCharacterMotionEntity(memberEntity, characterMotionEntity2)).willReturn(gainedCharacterMotionEntity2);

        //when
        CharacterMotionsResponse characterMotionsResponse = characterMotionUseCase.getMotions(memberEntity.getId(), characterEntity.getId());

        //then
        Assertions.assertThat(characterMotionsResponse.gainedCharacterMotions()).contains(CharacterMotionResponse.of(PlaceCategory.CAFFE.name(), characterMotionEntity1.getMotionCaptureImageUrl(), true));
        Assertions.assertThat(characterMotionsResponse.gainedCharacterMotions()).contains(CharacterMotionResponse.of(PlaceCategory.CULTURE.name(), characterMotionEntity2.getMotionCaptureImageUrl(), true));
        Assertions.assertThat(characterMotionsResponse.gainedCharacterMotions()).doesNotContain(CharacterMotionResponse.of(PlaceCategory.RESTAURANT.name(), characterMotionEntity3.getNotGainedMotionThumbnailImageUrl(), false));

    }

}
