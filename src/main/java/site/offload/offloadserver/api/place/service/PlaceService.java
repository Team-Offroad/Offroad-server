package site.offload.offloadserver.api.place.service;

import org.springframework.stereotype.Component;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.db.place.entity.PlaceCategory;

@Component
public class PlaceService {

    public PlaceCategory getPlaceCategory(final String category) {
        final PlaceCategory placeCategory;
        try {
            placeCategory = PlaceCategory.valueOf(category);
        } catch (final IllegalArgumentException e) {
            throw new NotFoundException(ErrorMessage.PLACE_CATEGORY_NOTFOUND_EXCEPTION);
        }
        return placeCategory;
    }
}
