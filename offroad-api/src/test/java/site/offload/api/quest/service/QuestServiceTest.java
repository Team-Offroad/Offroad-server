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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

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

    private QuestEntity createQuest(boolean isQuestSamePlace, String name, PlaceCategory placeCategory, PlaceArea placeArea, int totalRequiredClearCount) {
        return QuestEntity.builder()
                .isQuestSamePlace(isQuestSamePlace)
                .name(name)
                .placeCategory(placeCategory)
                .placeArea(placeArea)
                .totalRequiredClearCount(totalRequiredClearCount)
                .build();
    }
}