package sites.offload.db.quest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sites.offload.db.quest.entity.Quest;
import sites.offload.enums.PlaceArea;
import sites.offload.enums.PlaceCategory;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Integer> {

    List<Quest> findAllByPlaceCategory(PlaceCategory placeCategory);

    List<Quest> findAllByPlaceArea(PlaceArea placeArea);

    List<Quest> findAllByPlaceAreaAndPlaceCategory(PlaceArea placeArea, PlaceCategory placeCategory);
}
