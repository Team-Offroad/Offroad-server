package site.offload.enums.place;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PlaceConstants {

    RANGE_LATITUDE(0.085),
    RANGE_LONGITUDE(0.085);

    private final double range;
}
