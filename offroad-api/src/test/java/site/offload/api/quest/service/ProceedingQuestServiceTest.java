package site.offload.api.quest.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.fixture.MemberEntityFixtureCreator;
import site.offload.api.fixture.ProceedingQuestEntityFixture;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.entity.ProceedingQuestEntity;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.db.quest.repository.ProceedingQuestRepository;
import site.offload.enums.member.SocialPlatform;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

import java.util.List;

import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static site.offload.api.fixture.QuestEntityFixtureCreator.createQuest;

@ExtendWith(MockitoExtension.class)
class ProceedingQuestServiceTest {

    @InjectMocks
    private ProceedingQuestService proceedingQuestService;

    @Mock
    private ProceedingQuestRepository proceedingQuestRepository;

    @Test
    @DisplayName("멤버 id로 모든 진행 중인 퀘스트를 조회할 수 있다.")
    void findAllByMemberId() {
        //given
        MemberEntity memberEntity1 = MemberEntityFixtureCreator.createMemberEntity("sub", "email", SocialPlatform.GOOGLE, "name");
        QuestEntity questEntity1 = createQuest(true, "test", PlaceCategory.NONE, PlaceArea.NONE, 1);
        QuestEntity questEntity2 = createQuest(false, "test", PlaceCategory.CAFFE, PlaceArea.NONE, 2);
        ProceedingQuestEntity proceedingQuestEntity1 = ProceedingQuestEntityFixture.createProceedingQuest(memberEntity1, questEntity1);
        ProceedingQuestEntity proceedingQuestEntity2 = ProceedingQuestEntityFixture.createProceedingQuest(memberEntity1, questEntity2);

        given(proceedingQuestRepository.findAllByMemberEntityId(anyLong())).willReturn(List.of(proceedingQuestEntity1, proceedingQuestEntity2));

        //when
        List<ProceedingQuestEntity> expectedList = proceedingQuestService.findAllByMemberId(1L);

        //then
        Assertions.assertThat(expectedList).containsExactly(proceedingQuestEntity1, proceedingQuestEntity2);
    }
}