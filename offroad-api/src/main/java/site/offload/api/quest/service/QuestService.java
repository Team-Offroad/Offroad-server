package site.offload.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.api.exception.NotFoundException;
import sites.offload.db.quest.entity.ProceedingQuest;
import sites.offload.db.quest.entity.Quest;
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

    public List<ProceedingQuest> findProceedingQuests(Long memberId) {
        return proceedingQuestRepository.findAllByMemberIdOrderByUpdatedAtDesc(memberId);
    }

    public List<Quest> findAllByPlaceCategory(PlaceCategory placeCategory) {
        return questRepository.findAllByPlaceCategory(placeCategory);
    }

    public List<Quest> findAllByPlaceArea(PlaceArea placeArea) {
        return questRepository.findAllByPlaceArea(placeArea);
    }

    public List<Quest> findAllByPlaceAreaAndPlaceCategory(PlaceCategory placeCategory, PlaceArea placeArea) {
        return questRepository.findAllByPlaceAreaAndPlaceCategory(placeArea, placeCategory);
    }

    public Quest findById(Integer id) {
        return questRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.QUEST_NOTFOUND_EXCEPTION)
        );
    }

}
