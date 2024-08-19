package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.exception.NotFoundException;
import site.offload.db.quest.entity.ProceedingQuestEntity;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.db.quest.repository.ProceedingQuestRepository;
import site.offload.db.quest.repository.QuestRepository;
import site.offload.enums.response.ErrorMessage;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final ProceedingQuestRepository proceedingQuestRepository;
    private final QuestRepository questRepository;

    public List<QuestEntity> findByIdNotIn(List<Integer> questIds) {
        return questRepository.findByIdNotIn(questIds);
    }

    public List<ProceedingQuestEntity> findProceedingQuests(Long memberId) {
        return proceedingQuestRepository.findAllByMemberEntityIdOrderByUpdatedAtDesc(memberId);
    }

    public List<QuestEntity> findAllByPlaceCategory(PlaceCategory placeCategory) {
        return questRepository.findAllByPlaceCategory(placeCategory);
    }

    public List<QuestEntity> findAllByPlaceArea(PlaceArea placeArea) {
        return questRepository.findAllByPlaceArea(placeArea);
    }

    public List<QuestEntity> findAllByPlaceAreaAndPlaceCategory(PlaceCategory placeCategory, PlaceArea placeArea) {
        return questRepository.findAllByPlaceAreaAndPlaceCategory(placeArea, placeCategory);
    }

    public QuestEntity findById(Integer id) {
        return questRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.QUEST_NOTFOUND_EXCEPTION)
        );
    }

}
