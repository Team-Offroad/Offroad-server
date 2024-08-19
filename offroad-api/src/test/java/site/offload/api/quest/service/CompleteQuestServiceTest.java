package site.offload.api.quest.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.api.fixture.CompleteQuestEntityFixture;
import site.offload.api.fixture.MemberEntityFixtureCreator;
import site.offload.db.member.entity.MemberEntity;
import site.offload.db.quest.entity.CompleteQuestEntity;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.db.quest.repository.CompleteQuestRepository;
import site.offload.enums.member.SocialPlatform;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

import java.util.List;

import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static site.offload.api.fixture.QuestEntityFixtureCreator.createQuest;

@ExtendWith(MockitoExtension.class)

public class CompleteQuestServiceTest {


    @InjectMocks
    private CompleteQuestService completeQuestService;

    @Mock
    private CompleteQuestRepository completeQuestRepository;

    @Test
    @DisplayName("멤버 id로 완료된 퀘스트를 모두 조호할 수 있다.")
    void findAllByMemberId() {

        //given
        MemberEntity memberEntity1 = MemberEntityFixtureCreator.createMemberEntity("sub", "email", SocialPlatform.GOOGLE, "name");
        QuestEntity questEntity1 = createQuest(true, "test", PlaceCategory.NONE, PlaceArea.NONE, 1);
        QuestEntity questEntity2 = createQuest(false, "test", PlaceCategory.CAFFE, PlaceArea.NONE, 2);
        CompleteQuestEntity completeQuestEntity1 = CompleteQuestEntityFixture.createCompleteQuestEntity(memberEntity1, questEntity1);
        CompleteQuestEntity completeQuestEntity2 = CompleteQuestEntityFixture.createCompleteQuestEntity(memberEntity1, questEntity2);

        given(completeQuestRepository.findAllByMemberEntityId(anyLong())).willReturn(List.of(completeQuestEntity1, completeQuestEntity2));

        //when
        List<CompleteQuestEntity> expectedList = completeQuestService.findAllByMemberId(1L);

        //then
        Assertions.assertThat(expectedList).containsExactly(completeQuestEntity1, completeQuestEntity2);
    }

}
