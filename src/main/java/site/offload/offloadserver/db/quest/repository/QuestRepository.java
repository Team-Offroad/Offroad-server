package site.offload.offloadserver.db.quest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.offloadserver.db.place.entity.PlaceArea;
import site.offload.offloadserver.db.place.entity.PlaceCategory;
import site.offload.offloadserver.db.quest.entity.Quest;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Integer> {

    List<Quest> findAllByPlaceCategory(PlaceCategory placeCategory);
    List<Quest> findAllByPlaceArea(PlaceArea placeArea);
    List<Quest> findAllByPlaceAreaAndPlaceCategory(PlaceArea placeArea, PlaceCategory placeCategory);
}
