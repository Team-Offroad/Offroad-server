package site.offload.api.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    public static long getElapsedDay(LocalDateTime startAt) {
        return ChronoUnit.DAYS.between(startAt, LocalDateTime.now()) + 1L;
    }
}
