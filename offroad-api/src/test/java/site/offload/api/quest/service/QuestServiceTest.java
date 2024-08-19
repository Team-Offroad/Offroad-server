package site.offload.api.quest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.db.quest.repository.ProceedingQuestRepository;
import site.offload.db.quest.repository.QuestRepository;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static site.offload.api.fixture.QuestEntityFixtureCreator.createQuest;

@ExtendWith(MockitoExtension.class)
class QuestServiceTest {

    @InjectMocks
    private QuestService questService;

    @Mock
    private QuestRepository questRepository;

    @Mock
    private ProceedingQuestRepository proceedingQuestRepository;

    @Test
    @DisplayName("퀘스트 id로 퀘스트를 조회할 수 있다.")
    void findById() {
        //given
        QuestEntity questEntity = createQuest(true, "test", PlaceCategory.NONE, PlaceArea.NONE, 1);
        given(questRepository.findById(anyInt())).willReturn(Optional.ofNullable(questEntity));

        //when
        QuestEntity expectedQuestEntity = questService.findById(1);

        //then
        assertThat(expectedQuestEntity).isEqualTo(questEntity);
    }

    @Test
    @DisplayName("특정 id 리스트가 포함되지 않은 퀘스트들을 조회할 수 있다")
    void findByIdNotIn() {
        //given
        QuestEntity questEntity1 = createQuest(false, "test", PlaceCategory.CAFFE, PlaceArea.NONE, 1);
        QuestEntity questEntity2 = createQuest(false, "test", PlaceCategory.NONE, PlaceArea.AREA1, 1);

        given(questRepository.findByIdNotIn(anyCollection())).willReturn(List.of(questEntity1, questEntity2));

        //when
        List<QuestEntity> expectedList = questService.findByIdNotIn(List.of(1, 4));

        //then
        assertThat(expectedList).containsExactly(questEntity1, questEntity2);
    }
}