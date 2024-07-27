package sites.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.quest.entity.QuestEntity;
import sites.offload.enums.PlaceArea;
import sites.offload.enums.PlaceCategory;

import java.util.List;

public interface QuestRepository extends JpaRepository<QuestEntity, Integer> {

    List<QuestEntity> findAllByPlaceCategory(PlaceCategory placeCategory);

    List<QuestEntity> findAllByPlaceArea(PlaceArea placeArea);

    List<QuestEntity> findAllByPlaceAreaAndPlaceCategory(PlaceArea placeArea, PlaceCategory placeCategory);
}
