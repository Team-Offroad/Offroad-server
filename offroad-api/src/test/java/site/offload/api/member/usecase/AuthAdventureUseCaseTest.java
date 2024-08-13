package site.offload.api.member.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.character.service.CharacterService;
import site.offload.api.charactermotion.service.CharacterMotionService;
import site.offload.api.charactermotion.service.GainedCharacterMotionService;
import site.offload.api.coupon.service.CouponService;
import site.offload.api.coupon.service.GainedCouponService;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.exception.BadRequestException;
import site.offload.api.member.dto.request.AuthAdventureRequest;
import site.offload.api.member.dto.response.VerifyQrcodeResponse;
import site.offload.api.member.service.MemberService;
import site.offload.api.place.service.PlaceService;
import site.offload.api.place.service.VisitedPlaceService;
import site.offload.api.quest.dto.response.CompleteQuestResponse;
import site.offload.api.quest.service.CompleteQuestService;
import site.offload.api.quest.service.ProceedingQuestService;
import site.offload.api.quest.service.QuestRewardService;
import site.offload.api.quest.service.QuestService;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.place.entity.PlaceEntity;
import site.offload.db.quest.entity.ProceedingQuestEntity;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.enums.member.SocialPlatform;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;
import site.offload.enums.response.ErrorMessage;
import site.offload.external.aws.S3Service;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.*;
import static site.offload.api.fixture.CharacterEntityFixtureCreator.createCharacterEntity;
import static site.offload.api.fixture.MemberEntityFixtureCreator.createMemberEntity;
import static site.offload.api.fixture.PlaceEntityFixtureCreator.createPlace;
import static site.offload.api.fixture.QuestEntityFixtureCreator.createQuest;

@ExtendWith(MockitoExtension.class)
class AuthAdventureUseCaseTest {

    @InjectMocks
    private AuthAdventureUseCase authAdventureUseCase;

    @Mock
    private MemberService memberService;
    @Mock
    private CharacterService characterService;
    @Mock
    private CharacterMotionService characterMotionService;
    @Mock
    private GainedCharacterMotionService gainedCharacterMotionService;
    @Mock
    private PlaceService placeService;
    @Mock
    private VisitedPlaceService visitedPlaceService;
    @Mock
    private QuestService questService;
    @Mock
    private ProceedingQuestService proceedingQuestService;
    @Mock
    private S3Service s3Service;
    @Mock
    private QuestRewardService questRewardService;
    @Mock
    private CompleteQuestService completeQuestService;
    @Mock
    private GainedCouponService gainedCouponService;
    @Mock
    private GainedEmblemService gainedEmblemService;
    @Mock
    private CouponService couponService;

    @Test
    @DisplayName("사용자가 탐험 인증 시 인증 성공 여부, 성공 여부에 따른 캐릭터 이미지, 완료된 퀘스트 목록을 가져올 수 있다.")
    void authAdventure() {
        //given
        AuthAdventureRequest validRequest = new AuthAdventureRequest(1L, 126.9, 37.5, "offroadCode");
        AuthAdventureRequest invalidDistanceRequest = new AuthAdventureRequest(1L, 126.5, 37.5, "offroadCode");
        AuthAdventureRequest invalidQrcodeRequest = new AuthAdventureRequest(1L, 126.9, 37.5, "invalidCode");

        MemberEntity memberEntity = createMemberEntity("sub", "text@test.com", SocialPlatform.GOOGLE, "kim");
        PlaceEntity placeEntity = createPlace(PlaceArea.AREA1,
                PlaceCategory.CAFFE,
                "test name1",
                "test address1",
                "test intro1",
                "offroadCode",
                37.50000001,
                126.90000001,
                "testurl1",
                "testAuthCode1");
        CharacterEntity characterEntity = createCharacterEntity(
                "characterName",
                "characterCode",
                "characterAdventureSuccessImageUrl",
                "characterBaseImageUrl",
                "characterSelectImageUrl",
                "characterSpotLightImageUrl",
                "characterAdventureQRFailureImageUrl",
                "notGainedCharacterThumbnailImageUrl",
                "characterDescription",
                "characterAdventureLocationFailureImageUrl",
                "캐릭터 메인 색깔 코드",
                "캐릭터 서브 색깔 코드",
                "캐릭터 요약 설명",
                "캐릭터 아이콘 이미지"
        );

        QuestEntity questEntity1 = createQuest(
                false,
                "예시 퀘스트1: 첫 카페 방문",
                PlaceCategory.CAFFE,
                PlaceArea.NONE,
                1
        );
        QuestEntity questEntity2 = createQuest(
                false,
                "예시 퀘스트2: 첫 1구역 방문",
                PlaceCategory.NONE,
                PlaceArea.AREA1,
                1
        );
        QuestEntity questEntity3 = createQuest(
                true,
                "예시 퀘스트3: 탐험 1개를 성공하였을 시",
                PlaceCategory.NONE,
                PlaceArea.NONE,
                1
        );
        QuestEntity questEntity4 = createQuest(
                true,
                "예시 퀘스트4: 탐험 2개를 성공하였을 시",
                PlaceCategory.NONE,
                PlaceArea.NONE,
                2
        );

        given(placeService.findPlaceById(anyLong())).willReturn(placeEntity);
        given(memberService.findById(anyLong())).willReturn(memberEntity);
        given(characterService.findByName(any())).willReturn(characterEntity);
        given(questService.findAllByPlaceCategory(any())).willReturn(List.of(questEntity1));
        given(questService.findAllByPlaceArea(any())).willReturn(List.of(questEntity2));
        given(questService.findAllByPlaceAreaAndPlaceCategory(any(), any())).willReturn(List.of(questEntity3, questEntity4));
        given(proceedingQuestService.save(any(ProceedingQuestEntity.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        given(s3Service.getPresignUrl("characterAdventureSuccessImageUrl")).willReturn("presignedCharacterAdventureSuccessImageUrl");
        given(s3Service.getPresignUrl("characterAdventureQRFailureImageUrl")).willReturn("presignedCharacterAdventureQRFailureImageUrl");

        //when
        List<CompleteQuestResponse> expectedCompleteQuestResponses = List.of(
                CompleteQuestResponse.of(questEntity1.getName()),
                CompleteQuestResponse.of(questEntity2.getName()),
                CompleteQuestResponse.of(questEntity3.getName())
        );
        VerifyQrcodeResponse expectedValidResponse = new VerifyQrcodeResponse(true, "presignedCharacterAdventureSuccessImageUrl", expectedCompleteQuestResponses);
        VerifyQrcodeResponse expectedInvalidQrcodeResponse = new VerifyQrcodeResponse(false, "presignedCharacterAdventureQRFailureImageUrl", null);
        VerifyQrcodeResponse actualValidRequestResponse = authAdventureUseCase.authAdventure(1L, validRequest);
        VerifyQrcodeResponse actualInvalidQrcodeRequestResponse = authAdventureUseCase.authAdventure(1L, invalidQrcodeRequest);

        //then
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> authAdventureUseCase.authAdventure(1L, invalidDistanceRequest))
                .withMessage(ErrorMessage.NOT_ALLOWED_DISTANCE_EXCEPTION.getMessage());

        assertThat(actualValidRequestResponse).usingRecursiveComparison()
                .ignoringFields("completeQuestList")
                .isEqualTo(expectedValidResponse);

        assertThat(actualValidRequestResponse.completeQuestList())
                .containsExactlyInAnyOrderElementsOf(expectedCompleteQuestResponses);

        assertThat(expectedInvalidQrcodeResponse).usingRecursiveComparison()
                .isEqualTo(actualInvalidQrcodeRequestResponse);
    }

}