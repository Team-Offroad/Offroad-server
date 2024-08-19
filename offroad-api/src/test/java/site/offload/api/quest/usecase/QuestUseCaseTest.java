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
import static org.mockito.BDDMockito.*;
import static site.offload.api.fixture.QuestEntityFixtureCreator.createQuest;

@ExtendWith(MockitoExtension.class)
public class QuestUseCaseTest {

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
    void getCompletedQuestList() {

        //given
        MemberEntity memberEntity1 = MemberEntityFixtureCreator.createMemberEntity("sub", "email", SocialPlatform.GOOGLE, "name");
        QuestEntity questEntity1 = createQuest(true, "test1", PlaceCategory.NONE, PlaceArea.NONE, 100);
        QuestEntity questEntity2 = createQuest(false, "test2", PlaceCategory.CAFFE, PlaceArea.NONE, 50);
        QuestEntity questEntity3 = createQuest(false, "test3", PlaceCategory.CAFFE, PlaceArea.NONE, 10);

        ProceedingQuestEntity proceedingQuestEntity1 = ProceedingQuestEntityFixture.createProceedingQuest(memberEntity1, questEntity1);
        ProceedingQuestEntity proceedingQuestEntity2 = ProceedingQuestEntityFixture.createProceedingQuest(memberEntity1, questEntity2);
        ProceedingQuestEntity proceedingQuestEntity3 = ProceedingQuestEntityFixture.createProceedingQuest(memberEntity1, questEntity3);

        given(proceedingQuestService.findAllByMemberId(anyLong())).willReturn(List.of(proceedingQuestEntity1, proceedingQuestEntity2, proceedingQuestEntity3));

        QuestDetailListResponse questDetailList = questUseCase.getQuestDetailList(1L, true);
        List<QuestDetailResponse> actualResponse = questDetailList.questList();

        List<String> expectedQuestNames = new ArrayList<>();
        for (QuestDetailResponse questDetailResponse : actualResponse) {
            expectedQuestNames.add(questDetailResponse.questName());
        }

        assertThat(expectedQuestNames).containsExactly("test3", "test2", "test1");
    }

    @Test
    @DisplayName("사용자는 진행도 100% 미만 퀘스트를 목록 id오름차순으로 목록 위쪽에, 완료된 퀘스트를 아래쪽에서 조회할 수 있다")
    void getAllQuest() {
        // given
        MemberEntity memberEntity1 = MemberEntityFixtureCreator.createMemberEntity("sub", "email", SocialPlatform.GOOGLE, "name");
        QuestEntity questEntity1 = Mockito.spy(createQuest(true, "test1", PlaceCategory.NONE, PlaceArea.NONE, 100));
        QuestEntity questEntity2 = Mockito.spy(createQuest(false, "test2", PlaceCategory.CAFFE, PlaceArea.NONE, 50));
        QuestEntity questEntity3 = Mockito.spy(createQuest(false, "test3", PlaceCategory.CAFFE, PlaceArea.NONE, 10));
        QuestEntity questEntity4 = Mockito.spy(createQuest(false, "test4", PlaceCategory.CAFFE, PlaceArea.NONE, 10));

        Mockito.doReturn(4).when(questEntity1).getId();
        Mockito.doReturn(3).when(questEntity2).getId();
        Mockito.doReturn(2).when(questEntity3).getId();
        Mockito.doReturn(1).when(questEntity4).getId();

        //questEntity1,2 완료 가정
        CompleteQuestEntity completeQuestEntity1 = CompleteQuestEntityFixture.createCompleteQuestEntity(memberEntity1, questEntity1);
        CompleteQuestEntity completeQuestEntity2 = CompleteQuestEntityFixture.createCompleteQuestEntity(memberEntity1, questEntity2);

        //questEntity3 진행 중, questEntity4는 시작 전 가정(proceedingQuestEntity 없음)
        ProceedingQuestEntity proceedingQuestEntity3 = ProceedingQuestEntityFixture.createProceedingQuest(memberEntity1, questEntity3);

        given(memberService.findById(anyLong())).willReturn(memberEntity1);
        given(completeQuestService.findAllByMemberId(anyLong())).willReturn(List.of(completeQuestEntity1, completeQuestEntity2));
        given(questService.findByIdNotIn(anyList()))
                .willReturn(List.of(questEntity3, questEntity4));

        given(proceedingQuestService.existsByMemberAndQuest(memberEntity1, questEntity1)).willReturn(false);
        given(proceedingQuestService.existsByMemberAndQuest(memberEntity1, questEntity2)).willReturn(false);
        given(proceedingQuestService.existsByMemberAndQuest(memberEntity1, questEntity3)).willReturn(true);
        given(proceedingQuestService.existsByMemberAndQuest(memberEntity1, questEntity4)).willReturn(false);

        given(proceedingQuestService.findByMemberAndQuest(memberEntity1, questEntity3)).willReturn(proceedingQuestEntity3);

        // when
        QuestDetailListResponse result = questUseCase.getQuestDetailList(1L, false);

        // then
        // 완료된 퀘스트 test1, test2는 뒤 쪽에 있고 진행 중이거나 시작전인 퀘스트는 id 오름차순으로 앞쪽에 정렬
        assertThat(result.questList())
                .hasSize(4)
                .extracting(QuestDetailResponse::questName)
                .containsExactly("test4", "test3", "test1", "test2");

        //각 퀘스트의 진행도 표시
        assertThat(result.questList())
                .extracting(QuestDetailResponse::currentCount)
                .containsExactly(0, 1, 100, 50);

    }

}
