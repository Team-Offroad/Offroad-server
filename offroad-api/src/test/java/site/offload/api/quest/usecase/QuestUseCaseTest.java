package site.offload.api.quest.usecase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.fixture.CompleteQuestEntityFixture;
import site.offload.api.fixture.MemberEntityFixtureCreator;
import site.offload.api.fixture.ProceedingQuestEntityFixture;
import site.offload.api.member.service.MemberService;
import site.offload.api.quest.dto.request.QuestDetailListRequest;
import site.offload.api.quest.dto.response.QuestDetailListResponse;
import site.offload.api.quest.dto.response.QuestDetailResponse;
import site.offload.api.quest.service.CompleteQuestService;
import site.offload.api.quest.service.ProceedingQuestService;
import site.offload.api.quest.service.QuestService;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.entity.CompleteQuestEntity;
import site.offload.db.quest.entity.ProceedingQuestEntity;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.enums.member.SocialPlatform;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.anyList;
import static org.mockito.BDDMockito.given;
import static site.offload.api.fixture.QuestEntityFixtureCreator.createQuest;

@ExtendWith(MockitoExtension.class)
class QuestUseCaseTest {

    @InjectMocks
    private QuestUseCase questUseCase;

    @Mock
    private QuestService questService;

    @Mock
    private ProceedingQuestService proceedingQuestService;

    @Mock
    private CompleteQuestService completeQuestService;

    @Mock
    private MemberService memberService;

    @Test
    @DisplayName("사용자는 진행중인 퀘스트를 달성도 기준 내림차순으로 조회할 수 있다")
    void getOngoingQuestList() {

        //given
        Long memberId = 1L;
        MemberEntity memberEntity = MemberEntityFixtureCreator.createMemberEntity("sub", "email", SocialPlatform.GOOGLE, "name");
        QuestEntity questEntity1 = createQuest(true, "test1", PlaceCategory.NONE, PlaceArea.NONE, 100);
        QuestEntity questEntity2 = createQuest(false, "test2", PlaceCategory.CAFFE, PlaceArea.NONE, 50);
        QuestEntity questEntity3 = createQuest(false, "test3", PlaceCategory.CAFFE, PlaceArea.NONE, 10);

        ProceedingQuestEntity proceedingQuestEntity1 = ProceedingQuestEntityFixture.createProceedingQuest(memberEntity, questEntity1);
        ProceedingQuestEntity proceedingQuestEntity2 = ProceedingQuestEntityFixture.createProceedingQuest(memberEntity, questEntity2);
        ProceedingQuestEntity proceedingQuestEntity3 = ProceedingQuestEntityFixture.createProceedingQuest(memberEntity, questEntity3);

        given(proceedingQuestService.findAllByMemberId(memberId)).willReturn(List.of(proceedingQuestEntity1, proceedingQuestEntity2, proceedingQuestEntity3));
        QuestDetailListRequest request = QuestDetailListRequest.of(10, true, 0);

        //when
        QuestDetailListResponse questDetailList = questUseCase.getQuestDetailList(memberId, request);
        List<QuestDetailResponse> actualResponse = questDetailList.questList();

        //then
        List<String> expectedQuestNames = actualResponse.stream()
                .map(QuestDetailResponse::questName)
                .toList();

        assertThat(expectedQuestNames).containsExactly("test3", "test2", "test1");
    }

    @Test
    @DisplayName("사용자는 완료되지 않은 퀘스트는 ID(pk) 오름차순으로 정렬, 완료된 퀘스트는 그 뒤에 정렬되어(순서상관x) 조회한다.")
    void getOrderedQuestListSortsQuests() {
        // given
        Long memberId = 1L;
        MemberEntity memberEntity = MemberEntityFixtureCreator.createMemberEntity("sub", "email", SocialPlatform.GOOGLE, "name");

        QuestEntity incompleteQuest1 = Mockito.spy(createQuest(false, "incomplete1", PlaceCategory.CAFFE, PlaceArea.NONE, 10));
        QuestEntity incompleteQuest2 = Mockito.spy(createQuest(false, "incomplete2", PlaceCategory.CAFFE, PlaceArea.NONE, 20));
        QuestEntity completedQuest1 = Mockito.spy(createQuest(true, "completed1", PlaceCategory.NONE, PlaceArea.NONE, 30));
        QuestEntity completedQuest2 = Mockito.spy(createQuest(true, "completed2", PlaceCategory.NONE, PlaceArea.NONE, 40));

        Mockito.doReturn(2).when(incompleteQuest1).getId();
        Mockito.doReturn(1).when(incompleteQuest2).getId();
        Mockito.doReturn(4).when(completedQuest1).getId();
        Mockito.doReturn(3).when(completedQuest2).getId();

        CompleteQuestEntity completeQuestEntity1 = CompleteQuestEntityFixture.createCompleteQuestEntity(memberEntity, completedQuest1);
        CompleteQuestEntity completeQuestEntity2 = CompleteQuestEntityFixture.createCompleteQuestEntity(memberEntity, completedQuest2);

        given(memberService.findById(memberId)).willReturn(memberEntity);
        given(completeQuestService.findAllByMemberId(memberId)).willReturn(List.of(completeQuestEntity1, completeQuestEntity2));
        given(questService.findByIdNotIn(anyList())).willReturn(List.of(incompleteQuest1, incompleteQuest2)); // id 2,1 순서로 쿼리
        QuestDetailListRequest request = QuestDetailListRequest.of(4, false, 0);

        // when
        List<QuestDetailResponse> result = questUseCase.getQuestDetailList(memberId, request).questList();

        // then
        assertThat(result)
                .hasSize(4)
                .extracting(QuestDetailResponse::questName)
                .containsExactly("incomplete2", "incomplete1", "completed1", "completed2");
    }


    @Test
    @DisplayName("사용자는 커서 기반 페이지네이션을 통해 진행중인 퀘스트 목록을 조회할 수 있다.")
    void getCursorPaginatedCompleteQuestList() {
        //given
        Long memberId = 1L;
        MemberEntity memberEntity = MemberEntityFixtureCreator.createMemberEntity("sub", "email", SocialPlatform.GOOGLE, "name");

        List<QuestEntity> questEntities = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            questEntities.add(createQuest(false, "test" + i, PlaceCategory.NONE, PlaceArea.NONE, 100));
        }

        List<ProceedingQuestEntity> proceedingQuestEntities = new ArrayList<>();
        for (QuestEntity questEntity : questEntities) {
            proceedingQuestEntities.add(ProceedingQuestEntityFixture.createProceedingQuest(memberEntity, questEntity));
        }

        given(proceedingQuestService.findAllByMemberId(memberId)).willReturn(proceedingQuestEntities);
        QuestDetailListRequest request1 = QuestDetailListRequest.of(2, true, 0);
        QuestDetailListRequest request2 = QuestDetailListRequest.of(2, true, 2);
        QuestDetailListRequest request3 = QuestDetailListRequest.of(2, true, 4);

        //when - 첫 페이지 (커서 0)
        QuestDetailListResponse firstPageDetailList = questUseCase.getQuestDetailList(memberId, request1);
        List<QuestDetailResponse> firstPageResponse = firstPageDetailList.questList();

        //then
        assertThat(firstPageResponse)
                .hasSize(2)  // 크기 확인
                .extracting(QuestDetailResponse::questName)
                .containsExactly("test1", "test2");  // 첫 요청 검증

        //when - 두 번째 페이지 (커서 2)
        QuestDetailListResponse secondPageDetailList = questUseCase.getQuestDetailList(memberId, request2);
        List<QuestDetailResponse> secondPageResponse = secondPageDetailList.questList();

        //then
        assertThat(secondPageResponse)
                .hasSize(2)  // 크기 확인
                .extracting(QuestDetailResponse::questName)
                .containsExactly("test3", "test4");  // 두 번째 요청 검증

        //when - 마지막 페이지 (커서 4)
        QuestDetailListResponse lastPageDetailList = questUseCase.getQuestDetailList(memberId, request3);
        List<QuestDetailResponse> lastPageResponse = lastPageDetailList.questList();

        //then
        assertThat(lastPageResponse)
                .hasSize(1)  // 마지막 페이지 크기 확인
                .extracting(QuestDetailResponse::questName)
                .containsExactly("test5");  // 세 번째 요청 검증
    }

    @Test
    @DisplayName("사용자는 커서기반 페이지네이션을 통해 전체 퀘스트 목록을 조회할 수 있다.")
    void getCursorPaginatedCompletedQuestList() {
        //given
        Long memberId = 1L;
        MemberEntity memberEntity = MemberEntityFixtureCreator.createMemberEntity("sub", "email", SocialPlatform.GOOGLE, "name");

        List<QuestEntity> questEntities = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            questEntities.add(createQuest(true, "test" + i, PlaceCategory.NONE, PlaceArea.NONE, i)); // 완료된 퀘스트
        }

        given(completeQuestService.findAllByMemberId(memberId)).willReturn(questEntities.stream()
                .map(questEntity -> CompleteQuestEntityFixture.createCompleteQuestEntity(memberEntity, questEntity)) // 적절한 Entity로 변환
                .toList());
        QuestDetailListRequest request1 = QuestDetailListRequest.of(2, false, 0);
        QuestDetailListRequest request2 = QuestDetailListRequest.of(2, false, 2);
        QuestDetailListRequest request3 = QuestDetailListRequest.of(2, false, 4);

        //when - 첫 페이지 (커서 0)
        QuestDetailListResponse firstPageDetailList = questUseCase.getQuestDetailList(memberId, request1);
        List<QuestDetailResponse> firstPageResponse = firstPageDetailList.questList();

        //then
        assertThat(firstPageResponse)
                .hasSize(2)
                .extracting(QuestDetailResponse::questName)
                .containsExactly("test1", "test2");  // 첫 요청 검증

        //when - 두 번째 페이지 (커서 2)
        QuestDetailListResponse secondPageDetailList = questUseCase.getQuestDetailList(memberId, request2);
        List<QuestDetailResponse> secondPageResponse = secondPageDetailList.questList();

        //then
        assertThat(secondPageResponse)
                .hasSize(2)
                .extracting(QuestDetailResponse::questName)
                .containsExactly("test3", "test4");  // 두 번째 요청 검증

        //when - 마지막 페이지 (커서 4)
        QuestDetailListResponse lastPageDetailList = questUseCase.getQuestDetailList(memberId, request3);
        List<QuestDetailResponse> lastPageResponse = lastPageDetailList.questList();

        //then
        assertThat(lastPageResponse)
                .hasSize(1)
                .extracting(QuestDetailResponse::questName)
                .containsExactly("test5");  // 세 번째 요청 검증
    }
}
