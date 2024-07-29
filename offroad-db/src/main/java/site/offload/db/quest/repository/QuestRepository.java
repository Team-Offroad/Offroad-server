package site.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import site.offload.db.quest.entity.QuestEntity;
import site.offload.enums.place.PlaceArea;
import site.offload.enums.place.PlaceCategory;

import java.util.List;

public interface QuestRepository extends JpaRepository<QuestEntity, Integer> {

    List<QuestEntity> findAllByPlaceCategory(PlaceCategory placeCategory);

    List<QuestEntity> findAllByPlaceArea(PlaceArea placeArea);

    List<QuestEntity> findAllByPlaceAreaAndPlaceCategory(PlaceArea placeArea, PlaceCategory placeCategory);
}
