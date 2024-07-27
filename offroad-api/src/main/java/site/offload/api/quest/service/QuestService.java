package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.exception.NotFoundException;
import sites.offload.db.quest.entity.ProceedingQuestEntity;
import sites.offload.db.quest.entity.QuestEntity;
import sites.offload.db.quest.repository.ProceedingQuestRepository;
import sites.offload.db.quest.repository.QuestRepository;
import sites.offload.enums.ErrorMessage;
import sites.offload.enums.PlaceArea;
import sites.offload.enums.PlaceCategory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final ProceedingQuestRepository proceedingQuestRepository;
    private final QuestRepository questRepository;

    public List<ProceedingQuestEntity> findProceedingQuests(Long memberId) {
        return proceedingQuestRepository.findAllByMemberIdOrderByUpdatedAtDesc(memberId);
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
