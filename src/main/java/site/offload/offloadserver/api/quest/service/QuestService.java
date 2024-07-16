package site.offload.offloadserver.api.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.db.place.entity.PlaceArea;
import site.offload.offloadserver.db.place.entity.PlaceCategory;
import site.offload.offloadserver.db.quest.entity.ProceedingQuest;
import site.offload.offloadserver.db.quest.entity.Quest;
import site.offload.offloadserver.db.quest.repository.ProceedingQuestRepository;
import site.offload.offloadserver.db.quest.repository.QuestRepository;

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
