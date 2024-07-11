package site.offload.offloadserver.api.place.dto.constans;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PlaceConstants {

    RANGE_LATITUDE(0.01),
    RANGE_LONGITUDE(0.005);

    private final double range;
}
